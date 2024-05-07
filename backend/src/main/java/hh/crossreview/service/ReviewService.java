package hh.crossreview.service;

import hh.crossreview.converter.ReviewConverter;
import hh.crossreview.dao.ReviewAttemptDao;
import hh.crossreview.dao.ReviewDao;
import hh.crossreview.dto.review.ReviewResolutionDto;
import hh.crossreview.dto.review.ReviewWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Named
@Singleton
public class ReviewService {
  private final RequirementsUtils reqUtils;

  private final SolutionService solutionService;

  private final ReviewDao reviewDao;

  private final ReviewAttemptDao reviewAttemptDao;

  private final ReviewConverter reviewConverter;

  public ReviewService(
          RequirementsUtils reqUtils,
          SolutionService solutionService,
          ReviewDao reviewDao,
          ReviewAttemptDao reviewAttemptDao,
          ReviewConverter reviewConverter) {
    this.reqUtils = reqUtils;
    this.solutionService = solutionService;
    this.reviewDao = reviewDao;
    this.reviewAttemptDao = reviewAttemptDao;
    this.reviewConverter = reviewConverter;
  }

  @Transactional
  public void createReview(Homework homework, User user){
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    reqUtils.requireValidCohorts(user.getCohorts(), homework);
    Solution solution = solutionService.requireSolutionExist(homework, user);
    reqUtils.requireEntityHasStatus(solution, String.valueOf(SolutionStatus.REVIEW_STAGE));
    requireReviewInProgressAlreadyExist(solution, user);
    solutionService.createSolutionAttempt(solution);
    Review review = new Review(user, solution);
    reviewDao.save(review);
  }

  @Transactional
  public void startReview(Homework homework, User user, Integer reviewId){
    Review review = requireReviewExist(reviewId);
    createReviewAttempt(homework, user, review);
    setStatus(review, ReviewStatus.REVIEW_STARTED);
  }

  @Transactional
  public void addReviewResolution(User user, Integer reviewId, ReviewResolutionDto reviewResolutionDto) {
    Review review = requireReviewExist(reviewId);
    String status = reviewResolutionDto.getStatus();
    String resolution = reviewResolutionDto.getResolution();
    if (!List.of(ReviewStatus.CORRECTIONS_REQUIRED.toString(), ReviewStatus.APPROVED.toString()).contains(status)) {
      throw new BadRequestException("Invalid status!");
    }
    review.setStatus(ReviewStatus.valueOf(status));
    ReviewAttempt reviewAttempt = reviewAttemptDao.findLastReviewAttemptByReview(review);
    reviewAttempt.setResolution(resolution);
    if (status.equals(ReviewStatus.APPROVED.toString())){
      reviewAttempt.setFinishedAt(LocalDateTime.now());
    }
  }

  public void createReviewAttempt(Homework homework, User user, Review review) {
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    Solution solution = solutionService.requireSolutionExist(homework, user);
    SolutionAttempt solutionAttempt = solution.getSolutionAttempts().getLast();
    ReviewAttempt reviewAttempt = new ReviewAttempt(review, solutionAttempt);
    reviewAttemptDao.save(reviewAttempt);
  }
  public ReviewWrapperDto wrapReviews(Homework homework, User user, List<Review> reviews) {
    Solution solution = solutionService.requireSolutionExist(homework, user);
    String commitId = solution.getSolutionAttempts().getLast().getCommitId();
    Integer projectId = homework.getHomeworkId();
    String sourceCommitId = homework.getSourceCommitId();
    return reviewConverter.convertToReviewWrapperDto(
            reviews, commitId, projectId, sourceCommitId
    );
  }

  @Transactional
  public ReviewWrapperDto getMyReviews(Homework homework, User user) {
    List<Review> reviews = reviewDao.findByHomeworkAndStudent(homework, user);
    return wrapReviews(homework, user, reviews);
  }

  @Transactional
  public ReviewWrapperDto getReviewsToDo(Homework homework, User user) {
    List<Review> reviews = reviewDao.findByHomeworkAndReviewer(homework, user);
    return wrapReviews(homework, user, reviews);
  }

  public void setStatus(Review review, ReviewStatus status) {
    reviewConverter.setStatus(review, status);
  }


  public void setReviewer(Review review, User reviewer) {
    reviewConverter.setReviewer(review, reviewer);
  }

  public void requireReviewInProgressAlreadyExist(Solution solution, User student){
    Optional<Review> reviewsInProgress =  reviewDao.findBySolutionStudent(solution, student)
            .stream()
            .filter(review -> ReviewStatus.getInProgressStatuses().contains(review.getStatus()))
            .findFirst();
    if (reviewsInProgress.isPresent()) {
      throw new ForbiddenException("You already have active reviews for this homework");
    }
  }
  public Review requireReviewExist(Integer reviewId) {
    Review review = reviewDao.find(Review.class, reviewId);
    if (review == null) {
      throw new BadRequestException("Review with this ID does not exist");
    }
    return review;
  }

  public void checkAdminStudentOrReviewerPermission(User user, Review review) {
    Integer userId = user.getUserId();
    if (!Objects.equals(userId, review.getReviewer().getUserId())
            && !Objects.equals(userId, review.getStudent().getUserId())
            && user.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }
  }
}

