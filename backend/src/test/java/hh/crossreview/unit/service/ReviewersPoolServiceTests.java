package hh.crossreview.unit.service;

import hh.crossreview.converter.ReviewersPoolConverter;
import hh.crossreview.dao.ReviewDao;
import hh.crossreview.dao.ReviewersPoolDao;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Reviewer;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewerStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.service.ReviewersPoolService;
import hh.crossreview.unit.TestsUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewersPoolServiceTests extends TestsUtil {

  @Mock
  private ReviewersPoolDao reviewersPoolDao;
  @Mock
  private ReviewDao reviewDao;
  @Mock
  private ReviewersPoolConverter reviewersPoolConverter;

  @InjectMocks
  private ReviewersPoolService reviewersPoolService;

  @Test
  void givenCreateReviewerCall_whenValid_thenSuccessfullyCreate() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));

    doNothing().when(reviewersPoolDao).save(any(Reviewer.class));
    when(reviewersPoolConverter.convertToPoolsReviewer(student, homework)).thenCallRealMethod();
    reviewersPoolService.createReviewer(student, homework);

    verify(reviewersPoolDao).save(any(Reviewer.class));
  }

  @Test
  void givenReleaseReviewerCall_whenValid_thenSuccessfullyRelease() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    Reviewer reviewer = createReviewer(1, ReviewerStatus.BUSY, LocalDateTime.now(), student, homework);

    when(reviewersPoolDao.findByUserAndHomework(student, homework)).thenReturn(Optional.of(reviewer));
    reviewersPoolService.releaseReviewer(student, homework);

    assertEquals(ReviewerStatus.AVAILABLE, reviewer.getStatus());
  }

  @Test
  void givenResolveReviewerCall_whenValid_thenSuccessfullyResolve() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    Reviewer reviewer = createReviewer(1, ReviewerStatus.AVAILABLE, LocalDateTime.now(), student, homework);

    when(reviewersPoolDao.findByUserAndHomework(student, homework)).thenReturn(Optional.of(reviewer));
    when(reviewDao.countInProgressReviewsByReviewerAndHomework(reviewer.getUser(), homework)).thenReturn(1L);
    reviewersPoolService.resolveReviewer(student, homework);

    assertEquals(ReviewerStatus.COMPLETE, reviewer.getStatus());
  }

  @Test
  void givenAppointAvailableReviewerCall_whenAlmostBusy_thenBecomeBusy() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    Reviewer reviewer = createReviewer(1, ReviewerStatus.AVAILABLE, LocalDateTime.now(), student, homework);

    when(reviewersPoolDao.findAvailableReviewer(homework)).thenReturn(Optional.of(reviewer));
    when(reviewDao.countInProgressReviewsByReviewerAndHomework(reviewer.getUser(), homework)).thenReturn(1L);
    User user = reviewersPoolService.appointAvailableReviewer(homework);

    assertEquals(ReviewerStatus.BUSY, reviewer.getStatus());
    assertEquals(student.getUserId(), user.getUserId());
  }

  @Test
  void givenAppointAvailableReviewerCall_whenCanAppointToAnotherSolution_thenStayAvailable() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    Reviewer reviewer = createReviewer(1, ReviewerStatus.AVAILABLE, LocalDateTime.now(), student, homework);

    when(reviewersPoolDao.findAvailableReviewer(homework)).thenReturn(Optional.of(reviewer));
    when(reviewDao.countInProgressReviewsByReviewerAndHomework(reviewer.getUser(), homework)).thenReturn(0L);
    User user = reviewersPoolService.appointAvailableReviewer(homework);

    assertEquals(ReviewerStatus.AVAILABLE, reviewer.getStatus());
    assertEquals(student.getUserId(), user.getUserId());
  }

  @Test
  void givenAppointAvailableReviewer_whenHasNotAvailableReviewer_thenReturnsNull() {
    Homework homework = createBackHomework();

    when(reviewersPoolDao.findAvailableReviewer(homework)).thenReturn(Optional.empty());
    User user = reviewersPoolService.appointAvailableReviewer(homework);

    assertNull(user);
  }

  private Reviewer createReviewer(
      Integer id,
      ReviewerStatus reviewerStatus,
      LocalDateTime availableAt,
      User user,
      Homework homework
  ) {
    return new Reviewer()
        .setId(id)
        .setStatus(reviewerStatus)
        .setAppointedAt(availableAt)
        .setUser(user)
        .setHomework(homework);
  }

}
