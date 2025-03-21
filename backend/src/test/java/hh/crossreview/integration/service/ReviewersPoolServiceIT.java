package hh.crossreview.integration.service;

import hh.crossreview.dao.ReviewDao;
import hh.crossreview.dao.ReviewersPoolDao;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.Reviewer;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.ReviewerStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.integration.BaseIT;
import hh.crossreview.service.ReviewersPoolService;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class ReviewersPoolServiceIT extends BaseIT {

  @Inject
  private ReviewersPoolService reviewersPoolService;
  @Inject
  private ReviewersPoolDao reviewersPoolDao;
  @Inject
  private ReviewDao reviewDao;

  @BeforeEach
  void setup() {
  }

  @AfterEach
  void after() {
    clearDb();
  }

  void clearDb() {
    getEntityManager()
        .createNativeQuery("TRUNCATE TABLE cohort, homework, user_account, lecture, reviewers_pool CASCADE")
        .executeUpdate();
  }

  @Test
  void givenSeveralAvailableReviewers_whenAppointAvailableReviewerCalls_thenAppointByPriority() {
    Homework homeworkBack = createBackendHomework();
    insertHomeworkInDb(homeworkBack);
    User student1 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user1", "email1");
    User student2 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user2", "email2");
    User student3 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user3", "email3");
    User student4 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user4", "email4");
    Solution solution1 = createSolution(
        SolutionStatus.REVIEW_STAGE, 0, 0,
        homeworkBack, student1,
        1, "1", "1"
    );
    Solution solution2 = createSolution(
        SolutionStatus.REVIEW_STAGE, 0, 0,
        homeworkBack, student2,
        2, "2", "2"
    );
    reviewersPoolDao.save(solution1);
    reviewersPoolDao.save(solution2);
    reviewDao.save(student1);
    reviewDao.save(student2);
    reviewDao.save(student3);
    reviewDao.save(student4);
    LocalDateTime time2 = LocalDateTime.parse("2024-05-06T12:00:00");
    LocalDateTime time3 = LocalDateTime.parse("2024-05-07T12:00:00");
    LocalDateTime time4 = LocalDateTime.parse("2024-05-08T14:00:00");
    insertReviewerInDb(createReviewer(ReviewerStatus.BUSY, time2, student2, homeworkBack));
    insertReviewerInDb(createReviewer(ReviewerStatus.AVAILABLE, time3, student3, homeworkBack));
    insertReviewerInDb(createReviewer(ReviewerStatus.AVAILABLE, time4, student4, homeworkBack));

    User reviewer1 = appointAvailableReviewer(solution1);
    User reviewer2 = appointAvailableReviewer(solution1);
    User reviewer3 = appointAvailableReviewer(solution1);

    assertEquals(student3.getUsername(), reviewer1.getUsername());
    assertEquals(student4.getUsername(), reviewer2.getUsername());
    assertNull(reviewer3);
  }

  @Test
  void givenStudents_whenCreateReviewerCall_thenCreateAvailableReviewer() {
    Homework homeworkBack = createBackendHomework();
    insertHomeworkInDb(homeworkBack);
    User student1 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user1", "email1");
    User student2 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user2", "email2");
    insertUserInDb(student1);
    insertUserInDb(student2);

    reviewersPoolService.createReviewer(student1, homeworkBack);
    reviewersPoolService.createReviewer(student2, homeworkBack);
    User reviewer1 = reviewersPoolService.getReviewerFromPool(student1, homeworkBack);
    User reviewer2 = reviewersPoolService.getReviewerFromPool(student2, homeworkBack);

    assertEquals(student1.getUsername(), reviewer1.getUsername());
    assertEquals(student2.getUsername(), reviewer2.getUsername());
  }

  @Test
  void givenReviewersInDifferentHomeworks_whenAppointReviewer_thenReviewersNotIntersect() {
    Homework homeworkBack = createBackendHomework();
    Homework homeworkFront = createFrontendHomework();
    insertHomeworkInDb(homeworkBack);
    insertHomeworkInDb(homeworkFront);
    User student1 = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user1", "email1");
    User student2 = createUser(UserRole.STUDENT, homeworkFront.getCohorts().get(0), "user2", "email2");
    insertReviewerInDb(createReviewer(ReviewerStatus.AVAILABLE, LocalDateTime.now(), student1, homeworkBack));
    insertReviewerInDb(createReviewer(ReviewerStatus.AVAILABLE, LocalDateTime.now(), student2, homeworkFront));

    User reviewer1 = reviewersPoolService.getReviewerFromPool(student1, homeworkBack);
    User reviewer2 = reviewersPoolService.getReviewerFromPool(student2, homeworkFront);

    assertEquals(student1.getUsername(), reviewer1.getUsername());
    assertEquals(student2.getUsername(), reviewer2.getUsername());
  }

  User appointAvailableReviewer(Solution solution) {
    Homework homework = solution.getHomework();
    User student = solution.getStudent();
    User reviewer = reviewersPoolService.getReviewerFromPool(student, homework);
    if (reviewer != null) {
      reviewDao.save(createReview(student, reviewer, ReviewStatus.REVIEWER_FOUND, solution));
    }
    return reviewer;
  }

  void insertUserInDb(User student) {
    reviewersPoolDao.save(student);
  }

  void insertHomeworkInDb(Homework homework) {
    insertLectureInDb(homework.getLecture());
    reviewersPoolDao.save(homework);
  }

  void insertLectureInDb(Lecture lecture) {
    List<Cohort> cohorts = lecture.getCohorts();
    User lector = lecture.getLector();

    cohorts.forEach((cohort) ->  reviewersPoolDao.save(cohort));
    reviewersPoolDao.save(lector);
    reviewersPoolDao.save(lecture);
  }

  void insertReviewerInDb(Reviewer reviewer) {
    User user = reviewer.getUser();
    reviewersPoolDao.save(user);
    reviewersPoolDao.save(reviewer);
  }

}
