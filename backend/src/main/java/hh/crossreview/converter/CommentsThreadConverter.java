package hh.crossreview.converter;

import hh.crossreview.dto.comment.CommentDto;
import hh.crossreview.dto.commentsthread.CommentsThreadDto;
import hh.crossreview.dto.commentsthread.CommentsThreadWrapperDto;
import hh.crossreview.entity.Comment;
import hh.crossreview.entity.CommentsThread;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Comparator;
import java.util.List;

@Named
@Singleton
public class CommentsThreadConverter {
  public CommentsThreadDto convertToCommentsThreadDto(CommentsThread commentsThread) {
    List<CommentDto> commentDto = commentsThread
        .getComments()
        .stream()
        .map(this::convertToCommentDto)
        .sorted(Comparator.comparingInt(CommentDto::getCommentId))
        .toList();
    return new CommentsThreadDto()
        .setThreadId(commentsThread.getCommentsThreadId())
        .setAuthorId(commentsThread.getAuthorId())
        .setStatus(commentsThread.getStatus().toString())
        .setCommitSha(commentsThread.getCommitSha())
        .setFilePath(commentsThread.getFilePath())
        .setStartLine(commentsThread.getStartLine())
        .setStartSymbol(commentsThread.getStartSymbol())
        .setEndLine(commentsThread.getEndLine())
        .setEndSymbol(commentsThread.getEndSymbol())
        .setComments(commentDto);
  }

  public CommentDto convertToCommentDto(Comment comment){
    return new CommentDto()
        .setCommentId(comment.getCommentId())
        .setAuthorId(comment.getAuthorId())
        .setContent(comment.getContent())
        .setCreatedAt(comment.getCreatedAt())
        .setUpdatedAt(comment.getUpdatedAt());
  }

  public CommentsThreadWrapperDto convertToCommentsThreadWrapperDto(List<CommentsThread> commentsThreads){
    List<CommentsThreadDto> commentsThreadDtos = commentsThreads
            .stream()
            .map(this::convertToCommentsThreadDto)
            .toList();
    return new CommentsThreadWrapperDto(commentsThreadDtos);
  }

}
