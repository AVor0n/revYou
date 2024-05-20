package hh.crossreview.service;

import hh.crossreview.converter.ReviewersPoolConverter;
import hh.crossreview.dao.ReviewDao;
import hh.crossreview.dao.ReviewersPoolDao;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Reviewer;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewerStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import java.time.LocalDateTime;
import java.util.Optional;

@Named
@Singleton
public class ReviewersPoolService {

  private static final Integer MAX_NUM_OF_REVIEWS_BY_STUDENT = 2;

  private static final Integer MAX_NUM_OF_REVIEWS_BY_TEACHER = 5;

  private final ReviewersPoolDao reviewersPoolDao;
  private final ReviewDao reviewDao;
  private final ReviewersPoolConverter reviewersPoolConverter;

  public ReviewersPoolService(ReviewersPoolDao reviewersPoolDao, ReviewDao reviewDao, ReviewersPoolConverter reviewersPoolConverter) {
    this.reviewersPoolDao = reviewersPoolDao;
    this.reviewDao = reviewDao;
    this.reviewersPoolConverter = reviewersPoolConverter;
  }

  @SuppressWarnings({"unused"})
  public void createReviewer(User user, Homework homework) {
    Reviewer poolsReviewer = reviewersPoolConverter.convertToPoolsReviewer(user, homework);
    reviewersPoolDao.save(poolsReviewer);
  }

  @SuppressWarnings({"unused"})
  public void releaseReviewer(User user, Homework homework) {
    Reviewer reviewer = reviewersPoolDao
        .findByUserAndHomework(user, homework)
        .orElseThrow(() -> new InternalServerErrorException(
            "User is not a reviewer"
        ));
    makeReviewerAvailable(reviewer);
  }

  @SuppressWarnings({"unused"})
  public void resolveReviewer(User user, Homework homework) {
    reviewersPoolDao
        .findByUserAndHomework(user, homework)
        .ifPresent(reviewer -> makeReviewerComplete(reviewer, homework));
  }

  @SuppressWarnings({"unused"})
  public User getReviewerFromPool(User user, Homework homework) {
    Optional<Reviewer> optionalReviewer = reviewersPoolDao.findAvailableReviewer(user, homework);
    if (optionalReviewer.isEmpty()) {
      return null;
    }
    Reviewer reviewer = optionalReviewer.get();
    makeReviewerAppointed(reviewer, homework);
    return reviewer.getUser();
  }

  public User findAvailableReviewer(User user, Homework homework){
    Long reviewsByTeacher = reviewDao.countReviewsByTeacherAndHomework(homework);
    if (reviewsByTeacher < MAX_NUM_OF_REVIEWS_BY_TEACHER) {
      return homework.getAuthor();
    } else {
      return getReviewerFromPool(user, homework);
    }
  }

  private void makeReviewerAvailable(Reviewer reviewer) {
    reviewer.setStatus(ReviewerStatus.AVAILABLE);
    reviewersPoolDao.save(reviewer);
  }

  private void makeReviewerAppointed(Reviewer reviewer, Homework homework) {

    Long appointedReviews = reviewDao.countInProgressReviewsByReviewerAndHomework(
        reviewer.getUser(),
        homework
    );

    if (appointedReviews + 1 >= MAX_NUM_OF_REVIEWS_BY_STUDENT) {
      reviewer.setStatus(ReviewerStatus.BUSY);
    }
    reviewer.setAppointedAt(LocalDateTime.now());
    reviewersPoolDao.save(reviewer);
  }

  private void makeReviewerComplete(Reviewer reviewer, Homework homework) {
    Long appointedReviews = reviewDao.countInProgressReviewsByReviewerAndHomework(
        reviewer.getUser(),
        homework
    );
    if (appointedReviews > 1) {
      throw new BadRequestException("Reviewer has unreviewed solutions");
    }
    reviewer.setStatus(ReviewerStatus.COMPLETE);
    reviewersPoolDao.save(reviewer);
  }
}
