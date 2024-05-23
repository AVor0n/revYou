package hh.crossreview.unit;

import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewDuration;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.StudyDirection;
import hh.crossreview.entity.enums.UserRole;
import java.time.LocalDateTime;
import java.util.List;

public class TestsUtil {

  public Homework createFrontHomework() {
    Cohort frontCohort = createCohort(StudyDirection.FRONT);
    User lector = createUser(1, UserRole.TEACHER, frontCohort);
    Lecture lecture = createLecture(1, List.of(frontCohort), lector);
    return createHomework(1, lecture, lector, ReviewDuration.TWO_DAYS);
  }

  public Homework createBackHomework() {
    Cohort backCohort = createCohort(StudyDirection.BACK);
    User lector = createUser(2, UserRole.TEACHER, backCohort);
    Lecture lecture = createLecture(2, List.of(backCohort), lector);
    return createHomework(2, lecture, lector, ReviewDuration.TWO_DAYS);
  }

  public Homework createHomework(
      Integer homeworkId,
      Lecture lecture,
      User author,
      ReviewDuration reviewDuration
  ) {
    return new Homework()
        .setHomeworkId(homeworkId)
        .setLecture(lecture)
        .setAuthor(author)
        .setReviewDuration(reviewDuration);
  }

  public Lecture createLecture(
      Integer lectureId,
      List<Cohort> cohorts,
      User lector
  ) {
    return new Lecture()
        .setLectureId(lectureId)
        .setCohorts(cohorts)
        .setLector(lector);
  }

  public User createUser(
      Integer userId,
      UserRole userRole,
      Cohort cohort
  ) {
    User user = new User();
    user.setUserId(userId);
    user.setRole(userRole);
    user.setCohort(cohort);
    return user;
  }

  public Review createReview(
      Integer reviewId,
      ReviewStatus reviewStatus,
      Solution solution,
      List<ReviewAttempt> reviewAttempts,
      User reviewer,
      User student
  ) {
    var review = new Review();
    review.setReviewId(reviewId);
    review.setStatus(reviewStatus);
    review.setSolution(solution);
    review.setReviewAttempts(reviewAttempts);
    review.setReviewer(reviewer);
    review.setStudent(student);
    return review;
  }

  public ReviewAttempt createReviewAttempt(
      Integer reviewAttemptId,
      LocalDateTime createdAt,
      LocalDateTime finishedAt,
      String resolution,
      SolutionAttempt solutionAttempt,
      Review review
  ) {
    var reviewAttempt = new ReviewAttempt();
    reviewAttempt.setReviewAttemptId(reviewAttemptId);
    reviewAttempt.setCreatedAt(createdAt);
    reviewAttempt.setFinishedAt(finishedAt);
    reviewAttempt.setResolution(resolution);
    reviewAttempt.setSolutionAttempt(solutionAttempt);
    reviewAttempt.setReview(review);
    return reviewAttempt;
  }

  @SuppressWarnings("SameParameterValue")
  public Solution createSolution(
      Integer solutionId,
      SolutionStatus solutionStatus,
      User student,
      Integer projectId,
      String branch,
      List<SolutionAttempt> solutionAttempts
  ) {
    return new Solution()
        .setSolutionId(solutionId)
        .setStatus(solutionStatus)
        .setStudent(student)
        .setProjectId(projectId)
        .setBranch(branch)
        .setSolutionAttempts(solutionAttempts);
  }

  @SuppressWarnings("SameParameterValue")
  public SolutionAttempt createSolutionAttempt(
      String commitId,
      LocalDateTime createdAt
  ) {
    return new SolutionAttempt()
        .setCommitId(commitId)
        .setCreatedAt(createdAt);
  }

  public Cohort createCohort(StudyDirection studyDirection) {
    return new Cohort().setStudyDirection(studyDirection);
  }

}
