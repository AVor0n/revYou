package hh.crossreview.service;

import hh.crossreview.converter.ReviewConverter;
import hh.crossreview.converter.UserDetailConverter;
import hh.crossreview.dao.ReviewAttemptDao;
import hh.crossreview.dao.ReviewDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.review.ReviewResolutionDto;
import hh.crossreview.dto.review.ReviewWrapperDto;
import hh.crossreview.dto.review.ReviewerChangeDto;
import hh.crossreview.dto.review.info.ReviewInfoWrapperDto;
import hh.crossreview.dto.user.UserDetailWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.ReviewerStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;

@Named
@Singleton
public class ReviewService {


  private static final Integer APPROVE_SCORE_FOR_COMPLETE = 2;
  private static final Integer REVIEW_SCORE_FOR_COMPLETE = 2;

  private final RequirementsUtils reqUtils;

  private final SolutionService solutionService;

  private final UserService userService;

  private final ReviewersPoolService reviewersPoolService;

  private final ReviewDao reviewDao;

  private final SolutionDao solutionDao;

  private final ReviewAttemptDao reviewAttemptDao;

  private final ReviewConverter reviewConverter;

  private final UserDetailConverter userDetailConverter;

  public ReviewService(
          RequirementsUtils reqUtils,
          SolutionService solutionService,
          UserService userService,
          ReviewersPoolService reviewersPoolService,
          ReviewDao reviewDao, SolutionDao solutionDao,
          ReviewAttemptDao reviewAttemptDao,
          ReviewConverter reviewConverter, UserDetailConverter userDetailConverter) {
    this.reqUtils = reqUtils;
    this.solutionService = solutionService;
    this.userService = userService;
    this.reviewersPoolService = reviewersPoolService;
    this.reviewDao = reviewDao;
    this.solutionDao = solutionDao;
    this.reviewAttemptDao = reviewAttemptDao;
    this.reviewConverter = reviewConverter;
    this.userDetailConverter = userDetailConverter;
  }

  @Transactional
  public void requestReview(Homework homework, User user){
    Solution solution = solutionService.requireSolutionExist(homework, user);
    reqUtils.requireEntityHasStatus(solution, String.valueOf(SolutionStatus.IN_PROGRESS));
    requireReviewInProgressNotExist(solution, user);
    solution.setStatus(SolutionStatus.REVIEW_STAGE);
    createReview(homework, solution, user);
  }

  @Transactional
  public void startReview(User user, Integer reviewId){
    Review review = requireReviewExist(reviewId);
    reqUtils.requireEntityHasStatus(review, ReviewStatus.REVIEWER_FOUND.toString());
    requireValidReviewer(review.getReviewer(), user);
    createReviewAttempt(review);
    setStatus(review, ReviewStatus.REVIEW_STARTED);
  }

  @Transactional
  public void addReviewResolution(Homework homework, User reviewer, Integer reviewId, ReviewResolutionDto reviewResolutionDto) {
    Review review = requireReviewExist(reviewId);
    String status = reviewResolutionDto.getStatus();
    String resolution = reviewResolutionDto.getResolution();
    requireValidReviewer(review.getReviewer(), reviewer);
    requireValidResolutionStatus(status);
    requireValidReviewStatusForResolution(review);

    ReviewAttempt reviewAttempt = reviewAttemptDao.findLastReviewAttemptByReview(review);
    reviewAttempt.setResolution(resolution);
    if (status.equals(ReviewStatus.APPROVED.toString())) {
      updateReviewer(review, homework);
      updateStudent(review, homework);
      review.setStatus(ReviewStatus.APPROVED);
      reviewAttempt.setFinishedAt(LocalDateTime.now());
    }
    else if (status.equals(ReviewStatus.CORRECTIONS_REQUIRED.toString())) {
      review.setStatus(ReviewStatus.CORRECTIONS_REQUIRED);
    }
  }

  @Transactional
  public void uploadCorrections(Homework homework, User user, Integer reviewId) {
    Review review = requireReviewExist(reviewId);
    reqUtils.requireEntityHasStatus(review, ReviewStatus.CORRECTIONS_REQUIRED.toString());
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    Solution solution = solutionService.requireSolutionExist(homework, user);
    solutionService.createSolutionAttempt(solution);
    createReviewAttempt(review);
    setStatus(review, ReviewStatus.CORRECTIONS_LOADED);
  }

  @Transactional
  public void replaceReviewer(User privilegedUser, Homework homework, ReviewerChangeDto reviewerChangeDto) {
    reqUtils.requireAuthorPermissionOrAdmin(privilegedUser, homework);
    Integer reviewId = reviewerChangeDto.getReviewId();
    Integer reviewerId = reviewerChangeDto.getReviewerId();
    Review review = requireReviewExist(reviewId);

    User newReviewer = getAndValidateReviewerForAssignment(homework, reviewerId);
    if (!review.getStatus().equals(ReviewStatus.REVIEWER_SEARCH)) {
      clearPreviewsReviewer(homework, review);
    }

    tryAppointReviewer(review, newReviewer);
  }

  private User getAndValidateReviewerForAssignment(Homework homework, Integer reviewerId) {
    User reviewer = userService.findById(reviewerId);
    reqUtils.requireEntityNotNull(reviewer, String.format("Reviewer with id %d was not found", reviewerId));
    if (!reviewer.getRole().equals(UserRole.TEACHER) &&
        !reviewersPoolService.checkStatus(reviewer, homework, ReviewerStatus.COMPLETE)) {
      throw new BadRequestException("Reviewer has not completed homework yet");
    }
    return reviewer;
  }

  private void clearPreviewsReviewer(Homework homework, Review review) {
    User oldReviewer = review.getReviewer();
    if (oldReviewer.getRole().equals(UserRole.STUDENT) &&
        reviewersPoolService.checkStatus(oldReviewer, homework, ReviewerStatus.BUSY)) {
      reviewersPoolService.releaseReviewer(oldReviewer, homework);
    }
    review.getCommentsThreads().clear();
    review.getReviewAttempts().clear();
  }

  private void createReview(Homework homework, Solution solution, User user){
    solutionService.createSolutionAttempt(solution);
    Review review = new Review(user, solution);
    reviewDao.save(review);
    User vacantReviewer = reviewersPoolService.findAvailableReviewer(user, homework);
    tryAppointReviewer(review, vacantReviewer);
  }

  private void requireValidReviewStatusForResolution(Review review) {
    ReviewStatus reviewStatus = review.getStatus();
    if (!reviewStatus.equals(ReviewStatus.REVIEW_STARTED) &&
        !reviewStatus.equals(ReviewStatus.CORRECTIONS_LOADED)) {
      throw new BadRequestException("Resolution already sent or invalid review status");
    }
  }

  private void requireValidReviewer(User reviewer, User user) {
    if (!reviewer.getUserId().equals(user.getUserId())) {
      throw new ForbiddenException("User is not assigned as a reviewer");
    }
  }

  private void requireValidResolutionStatus(String status) {
    if (!List.of(ReviewStatus.CORRECTIONS_REQUIRED.toString(), ReviewStatus.APPROVED.toString()).contains(status)) {
      throw new BadRequestException("Invalid status!");
    }
  }

  private void updateStudent(Review review, Homework homework) {
    User student = review.getStudent();
    User reviewer = review.getReviewer();
    Solution solution = review.getSolution();
    int newApproveScore = reviewer.getRole().equals(UserRole.TEACHER) ?
        APPROVE_SCORE_FOR_COMPLETE :
        solution.getApproveScore() + 1;
    solution.setApproveScore(newApproveScore);
    if (newApproveScore >= APPROVE_SCORE_FOR_COMPLETE) {
      solution.setStatus(SolutionStatus.REVIEWER_STAGE);
      reviewersPoolService.createReviewer(student, homework);
      for(int i = 0; i < REVIEW_SCORE_FOR_COMPLETE; i++){
        Review reviewWithSearchingReviewer = getReviewWithStatusReviewSearch(homework);
        if (reviewWithSearchingReviewer == null){
          break;
        }
        tryAppointReviewer(reviewWithSearchingReviewer, student);
      }

    } else {
      createReview(homework, solution, student);
    }
  }

  private void updateReviewer(Review review, Homework homework) {
    User reviewer = review.getReviewer();
    if (reviewer.getRole().equals(UserRole.TEACHER)) {
      return;
    }
    Solution solution = solutionService.requireSolutionExist(homework, reviewer);
    Integer newReviewScore = solution.getReviewScore() + 1;
    solution.setReviewScore(newReviewScore);
    if (newReviewScore >= REVIEW_SCORE_FOR_COMPLETE) {
      solution.setStatus(SolutionStatus.COMPLETE);
      reviewersPoolService.resolveReviewer(reviewer, homework);
    }
    else {
      reviewersPoolService.releaseReviewer(reviewer, homework);
    }
  }

  private Review getReviewWithStatusReviewSearch(Homework homework){
    List<Review> searchingReviews = reviewDao.findReviewsByHomeworkAndStatus(
            homework,
            ReviewStatus.REVIEWER_SEARCH,
            REVIEW_SCORE_FOR_COMPLETE
    );
    if (searchingReviews.isEmpty()){
      return null;
    }
    return searchingReviews.getFirst();
  }

  public void createReviewAttempt(Review review) {
    Solution solution = review.getSolution();
    SolutionAttempt solutionAttempt = solution.getSolutionAttempts().getLast();
    ReviewAttempt reviewAttempt = new ReviewAttempt(review, solutionAttempt);
    reviewAttemptDao.save(reviewAttempt);
  }

  public ReviewWrapperDto wrapMyReviews(Homework homework, User user, List<Review> reviews) {
    Solution solution = solutionService.requireSolutionExist(homework, user);
    if (solution.getSolutionAttempts().isEmpty()) {
      return new ReviewWrapperDto(Collections.emptyList());
    }
    String commitId = solution.getSolutionAttempts().getLast().getCommitId();
    String sourceCommitId = homework.getSourceCommitId();
    return reviewConverter.convertToReviewWrapperDto(
            reviews, commitId, sourceCommitId
    );
  }

  private ReviewWrapperDto wrapReviewsToDo(Homework homework, List<Review> reviews) {
    String sourceCommitId = homework.getSourceCommitId();
    List<ImmutablePair<Review, String>> pairs = extractReviewCommitPairs(homework, reviews);
    return reviewConverter.convertToReviewWrapperDto(pairs, sourceCommitId);
  }

  private ReviewInfoWrapperDto wrapReviewInfo(Homework homework, List<Review> reviews) {
    List<ImmutablePair<Review, String>> pairs = extractReviewCommitPairs(homework, reviews);
    return reviewConverter.convertToReviewInfoWrapperDto(pairs, homework.getSourceCommitId());
  }

  private List<ImmutablePair<Review, String>> extractReviewCommitPairs(Homework homework, List<Review> reviews) {
    return reviews
        .stream()
        .map(review -> {
          String commitId = solutionService
              .requireSolutionExist(homework, review.getStudent())
              .getSolutionAttempts()
              .getLast()
              .getCommitId();
          return new ImmutablePair<>(review, commitId);
        })
        .toList();
  }

  @Transactional
  public ReviewWrapperDto getMyReviews(Homework homework, User user) {
    List<Review> reviews = reviewDao.findByHomeworkAndStudent(homework, user);
    return wrapMyReviews(homework, user, reviews);
  }

  @Transactional
  public ReviewWrapperDto getReviewsToDo(Homework homework, User user) {
    List<Review> reviews = reviewDao.findByHomeworkAndReviewer(homework, user);
    return wrapReviewsToDo(homework, reviews);
  }

  @Transactional
  public ReviewInfoWrapperDto getReviewsInfo(Homework homework, User user) {
    reqUtils.requireUserHasRole(user, UserRole.TEACHER);
    List<Review> reviews =  reviewDao.findByHomework(homework);
    return wrapReviewInfo(homework, reviews);
  }

  public UserDetailWrapperDto getAvailableReviewers(
          User user, Homework homework
  ) {
    reqUtils.requireAuthorPermissionOrAdmin(user, homework);
    List<User> studentsWithCompleteHw = solutionDao.getStudentsBySolutionStatusAndHomeworkId(
            SolutionStatus.COMPLETE, homework
    );
    List<User> availableReviewers = new ArrayList<>();
    availableReviewers.add(user);
    availableReviewers.addAll(studentsWithCompleteHw);
    return userDetailConverter.convertToUserDetailWrapperDto(availableReviewers);


  }

  public void setStatus(Review review, ReviewStatus status) {
    reviewConverter.setStatus(review, status);
  }

  public void tryAppointReviewer(Review review, User reviewer) {
    if (reviewer == null || review == null){
      return;
    }
    review.setReviewer(reviewer);
    review.setStatus(ReviewStatus.REVIEWER_FOUND);
    reviewDao.save(review);
  }

  public void requireReviewInProgressNotExist(Solution solution, User student){
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

  @Transactional
  public void approveStudent(Homework homework, User teacher, User student) {
    reqUtils.requireAuthorPermissionOrAdmin(teacher, homework);

    Solution solution = solutionService.requireSolutionExist(homework, student);
    solution.setStatus(SolutionStatus.COMPLETE);

    List<Review> reviewsByStudent = reviewDao.findByHomeworkAndReviewer(homework, student);
    recreateReviewsStartedByStudent(homework, reviewsByStudent);

    reviewersPoolService.resolveReviewer(student, homework);

    List<Review> reviewsForStudent = reviewDao.findByHomeworkAndStudent(homework, student);
    releaseReviewersAssignedForStudentSolution(homework, reviewsForStudent);
  }

  private void recreateReviewsStartedByStudent(Homework homework, List<Review> reviewsByStudent) {
    reviewsByStudent.forEach(review -> {
      if (!review.getStatus().equals(ReviewStatus.APPROVED)) {
        review.setStatus(ReviewStatus.ARCHIVED);
        createReview(homework, review.getSolution(), review.getStudent());
      }
    });
  }

  private void releaseReviewersAssignedForStudentSolution(Homework homework, List<Review> reviewsForStudent) {
    reviewsForStudent.forEach(review -> {
      if (!review.getStatus().equals(ReviewStatus.APPROVED)) {
        User reviewer = review.getReviewer();
        if (Objects.nonNull(reviewer) && reviewer.getRole().equals(UserRole.STUDENT)) {
          reviewersPoolService.releaseReviewer(review.getReviewer(), homework);
        }
        setStatus(review, ReviewStatus.ARCHIVED);
      }
    });
  }

}

