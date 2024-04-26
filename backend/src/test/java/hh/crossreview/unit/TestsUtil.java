package hh.crossreview.unit;

import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewDuration;
import hh.crossreview.entity.enums.StudyDirection;
import hh.crossreview.entity.enums.UserRole;
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

  public Cohort createCohort(StudyDirection studyDirection) {
    return new Cohort().setStudyDirection(studyDirection);
  }

}
