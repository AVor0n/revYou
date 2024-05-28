package hh.crossreview.service;

import hh.crossreview.converter.CommentsThreadConverter;
import hh.crossreview.dao.CommentDao;
import hh.crossreview.dao.CommentsThreadDao;
import hh.crossreview.dto.comment.CommentDto;
import hh.crossreview.dto.comment.CommentPostDto;
import hh.crossreview.dto.commentsthread.CommentsThreadDto;
import hh.crossreview.dto.commentsthread.CommentsThreadPostDto;
import hh.crossreview.dto.commentsthread.CommentsThreadResolveDto;
import hh.crossreview.dto.commentsthread.CommentsThreadWrapperDto;
import hh.crossreview.entity.Comment;
import hh.crossreview.entity.CommentsThread;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.CommentsThreadStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.EnumUtils;


@Named
@Singleton
public class CommentsThreadService {

  private final RequirementsUtils requirementsUtils;

  private final CommentDao commentDao;

  private final CommentsThreadDao commentsThreadDao;

  private final ReviewService reviewService;

  private final CommentsThreadConverter commentsThreadConverter;

  public CommentsThreadService(
      RequirementsUtils requirementsUtils,
      CommentDao commentDao,
      CommentsThreadDao commentsThreadDao,
      ReviewService reviewService,
      CommentsThreadConverter commentsThreadConverter
  ) {
    this.requirementsUtils = requirementsUtils;
    this.commentDao = commentDao;
    this.commentsThreadDao = commentsThreadDao;
    this.reviewService = reviewService;
    this.commentsThreadConverter = commentsThreadConverter;
  }

  @Transactional
  public CommentsThreadDto createCommentsThread(CommentsThreadPostDto commentsThreadPostDto, User author){
    Review review = reviewService.requireReviewExist(commentsThreadPostDto.getReviewId());
    checkReviewParticipantTeacherAdminPermission(author, review);
    CommentsThread commentsThread = new CommentsThread(
            author,
            review,
            commentsThreadPostDto.getCommitSha(),
            commentsThreadPostDto.getFilePath(),
            commentsThreadPostDto.getStartLine(),
            commentsThreadPostDto.getStartSymbol(),
            commentsThreadPostDto.getEndLine(),
            commentsThreadPostDto.getEndSymbol()
    );
    commentsThreadDao.save(commentsThread);
    Comment comment = createComment(commentsThread, author, commentsThreadPostDto.getContent());
    commentsThread.setComments(Collections.singletonList(comment));
    return commentsThreadConverter.convertToCommentsThreadDto(commentsThread);
  }

  @Transactional
  public CommentsThreadWrapperDto getAllThreads(User user, Integer reviewId){
    Review review = reviewService.requireReviewExist(reviewId);
    checkReviewParticipantTeacherAdminPermission(user, review);
    List<CommentsThread> commentsThreads = commentsThreadDao.findByReview(review);
    return commentsThreadConverter.convertToCommentsThreadWrapperDto(commentsThreads);
  }
  @Transactional
  public CommentDto addComment(
           CommentPostDto commentPostDto,
           Integer commentsThreadId,
           User author
  ) {
    CommentsThread commentsThread = requiredCommentsThreadExist(commentsThreadId);
    Review review = reviewService.requireReviewExist(commentsThread.getReview().getReviewId());
    checkReviewParticipantTeacherAdminPermission(author, review);
    Comment comment = createComment(commentsThread, author, commentPostDto.getContent());
    return commentsThreadConverter.convertToCommentDto(comment);
  }

  public Comment createComment(
            CommentsThread commentsThread,
            User author,
            String content
  ) {
    if (content.isEmpty()) {
      throw new BadRequestException("Content field cannot be empty!");
    }
    Comment comment = new Comment(commentsThread, author, content);
    commentDao.save(comment);
    return comment;
  }

  @Transactional
  public void deleteComment(User author, Integer commentId) {
    Comment comment = requireCommentExist(commentId);
    requirementsUtils.requireAuthorPermissionOrAdmin(author, comment);
    Integer commentsThreadId = comment.getCommentsThread().getCommentsThreadId();
    CommentsThread commentsThread = requiredCommentsThreadExist(commentsThreadId);
    if (commentsThread.getComments().size() == 1){
      commentsThreadDao.deleteCommentsThread(commentsThread);
      return;
    }
    commentDao.deleteComment(comment);
  }

  @Transactional
  public CommentDto updateComment(User author, Integer commentId, CommentPostDto commentPostDto) {
    Comment comment = requireCommentExist(commentId);
    requirementsUtils.requireAuthorPermissionOrAdmin(author, comment);
    String newContent = commentPostDto.getContent();
    if (newContent.isEmpty()) {
      throw new BadRequestException("Content field cannot be empty!");
    }
    comment.setContent(newContent);
    comment.setUpdatedAt(LocalDateTime.now());
    commentDao.save(comment);
    return  commentsThreadConverter.convertToCommentDto(comment);
  }

  @Transactional
  public CommentsThreadDto resolveThread(
          User author,
          Integer commentsThreadId,
          CommentsThreadResolveDto commentsThreadResolveDto
  ) {
    String newStatus = commentsThreadResolveDto.getStatus();
    if (!EnumUtils.isValidEnum(CommentsThreadStatus.class, newStatus)) {
      throw new BadRequestException("This status is not available for the thread!");
    }
    CommentsThread commentsThread = requiredCommentsThreadExist(commentsThreadId);
    Review review = reviewService.requireReviewExist(commentsThread.getReview().getReviewId());
    checkReviewParticipantTeacherAdminPermission(author, review);
    commentsThread.setStatus(CommentsThreadStatus.valueOf(newStatus));
    commentsThreadDao.save(commentsThread);
    return commentsThreadConverter.convertToCommentsThreadDto(commentsThread);
  }

  private Comment requireCommentExist(Integer commentId) {
    Comment comment = commentDao.find(Comment.class, commentId);
    if (comment == null) {
      throw new BadRequestException("Comment with this ID does not exist");
    }
    return comment;
  }

  private CommentsThread requiredCommentsThreadExist(Integer commentsThreadId){
    CommentsThread commentsThread = commentsThreadDao.find(CommentsThread.class, commentsThreadId);
    if (commentsThread == null) {
      throw new BadRequestException("CommentsThread with this ID does not exist");
    }
    return commentsThread;
  }

  private void checkReviewParticipantTeacherAdminPermission(User user, Review review) {
    if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.TEACHER)) {
      return;
    }
    List<Integer> allowedUserIds = Arrays.asList(
        review.getReviewer().getUserId(),
        review.getStudent().getUserId()
    );
    if (!allowedUserIds.contains(user.getUserId())) {
      throw new ForbiddenException("This action is not available to you!");
    }
  }
}
