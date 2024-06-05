package hh.crossreview.integration.resource;

import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.HomeworkPatchDto;
import hh.crossreview.dto.homework.HomeworkPostDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.StudyDirection;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.integration.BaseIT;
import hh.crossreview.service.HomeworkService;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class HomeworkResourceIT extends BaseIT {

  @Inject
  private HomeworkService homeworkService;
  @Inject
  private HomeworkDao homeworkDao;

  @BeforeEach
  void setup() {
  }

  @AfterEach
  void after() {
    clearDb();
  }

  void clearDb() {
    getEntityManager()
        .createNativeQuery("TRUNCATE TABLE cohort, homework, user_account, lecture CASCADE")
        .executeUpdate();
  }

  @Test
  void givenHomeworks_whenStudentGetHomeworks_thenReturnsHomeworksByCohort() {
    Homework homeworkBack = createBackendHomework();
    Homework homeworkFront = createFrontendHomework();
    insertHomeworkInDb(homeworkBack);
    insertHomeworkInDb(homeworkFront);

    User student = createUser(UserRole.STUDENT, homeworkBack.getCohorts().get(0), "user3", "email3");
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(student);

    assertEquals(1, homeworksWrapperDto.getData().size());
    assertEquals(homeworkBack.getHomeworkId(), homeworksWrapperDto.getData().get(0).getId());
  }

  @Test
  void givenHomeworks_whenTeacherGetHomeworks_thenReturnsHomeworksByAuthor() {
    Homework homeworkBack = createBackendHomework();
    Homework homeworkFront = createFrontendHomework();
    insertHomeworkInDb(homeworkBack);
    insertHomeworkInDb(homeworkFront);

    User teacher = homeworkBack.getAuthor();
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(teacher);

    assertEquals(1, homeworksWrapperDto.getData().size());
    assertEquals(teacher.getUserId(), homeworksWrapperDto.getData().get(0).getAuthor().getId());
  }

  @Test
  void givenHomeworks_whenAdminGetHomeworks_thenReturnsAllHomeworks() {
    Homework homeworkBack = createBackendHomework();
    Homework homeworkFront = createFrontendHomework();
    insertHomeworkInDb(homeworkBack);
    insertHomeworkInDb(homeworkFront);

    User admin = createUser(UserRole.ADMIN, homeworkBack.getCohorts().get(0), "user3", "email3");
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(admin);

    assertEquals(2, homeworksWrapperDto.getData().size());
  }

  @Test
  void givenHomeworkPostDto_whenCreateHomeworkCall_thenSuccessfullyCreate() {
    Cohort backCohort = createCohort(StudyDirection.BACK);
    User teacher = createUser(UserRole.TEACHER, backCohort, "user1", "email1");
    Lecture lecture = createLecture(teacher, List.of(backCohort));
    insertLectureInDb(lecture);
    HomeworkPostDto homeworkPostDto = createHomeworkPostDto("RepositoryLink", 48, lecture.getLectureId());
    String sourceCommitId = "aabbccddeeff";

    homeworkService.createHomework(homeworkPostDto, teacher, sourceCommitId);
    Homework homework = homeworkDao.findByAuthor(teacher).get(0);

    assertEquals(homeworkPostDto.getRepositoryLink(), homework.getRepositoryLink());
    assertEquals(homeworkPostDto.getReviewDuration(), homework.getReviewDuration().getHours());
    assertEquals(homeworkPostDto.getLectureId(), homework.getLecture().getLectureId());
    assertEquals(sourceCommitId, homework.getSourceCommitId());
  }

  @Test
  void givenHomeworkPatchDto_whenUpdateHomeworkCall_thenSuccessfullyUpdate() {
    HomeworkPatchDto homeworkPatchDto = createHomeworkPatchDto(
        "NewName",
        "NewTopic",
        "NewDesc",
        Date.from(Instant.parse("2024-04-25T00:00:00Z")),
        Date.from(Instant.parse("2024-04-30T00:00:00Z")),
        24
    );
    Homework homework = createBackendHomework();
    insertHomeworkInDb(homework);

    homeworkService.updateHomework(homework.getHomeworkId(), homeworkPatchDto, homework.getAuthor());

    assertEquals(homeworkPatchDto.getName(), homework.getName());
    assertEquals(homeworkPatchDto.getTopic(), homework.getTopic());
    assertEquals(homeworkPatchDto.getDescription(), homework.getDescription());
    assertEquals(
        homeworkPatchDto.getStartDate(),
        homework.getStartDate().toInstant().atOffset(ZoneOffset.UTC)
    );
    assertEquals(
        homeworkPatchDto.getCompletionDeadline(),
        homework.getCompletionDeadline().toInstant().atOffset(ZoneOffset.UTC)
    );
    assertEquals(homeworkPatchDto.getReviewDuration(), homework.getReviewDuration().getHours());
  }

  void insertHomeworkInDb(Homework homework) {
    insertLectureInDb(homework.getLecture());
    homeworkDao.save(homework);
  }

  void insertLectureInDb(Lecture lecture) {
    List<Cohort> cohorts = lecture.getCohorts();
    User lector = lecture.getLector();

    cohorts.forEach((cohort) ->  homeworkDao.save(cohort));
    homeworkDao.save(lector);
    homeworkDao.save(lecture);
  }

  @SuppressWarnings("SameParameterValue")
  HomeworkPostDto createHomeworkPostDto(
      String repositoryLink,
      Integer reviewDuration,
      Integer lectureId
  ) {
    HomeworkPostDto homeworkPostDto = new HomeworkPostDto();
    homeworkPostDto.setStartDate(Date.from(Instant.parse("2024-04-25T00:00:00Z")));
    homeworkPostDto.setCompletionDeadline(Date.from(Instant.parse("2024-04-30T00:00:00Z")));
    homeworkPostDto.setName("Name");
    homeworkPostDto.setTopic("Topic");
    homeworkPostDto.setRepositoryLink(repositoryLink);
    homeworkPostDto.setReviewDuration(reviewDuration);
    homeworkPostDto.setLectureId(lectureId);
    return homeworkPostDto;
  }

  @SuppressWarnings("SameParameterValue")
  HomeworkPatchDto createHomeworkPatchDto(
      String name,
      String topic,
      String description,
      Date startDate,
      Date completionDeadline,
      Integer reviewDuration
  ) {
    HomeworkPatchDto homeworkPatchDto = new HomeworkPatchDto();
    homeworkPatchDto.setName(name);
    homeworkPatchDto.setTopic(topic);
    homeworkPatchDto.setDescription(description);
    homeworkPatchDto.setStartDate(startDate);
    homeworkPatchDto.setCompletionDeadline(completionDeadline);
    homeworkPatchDto.setReviewDuration(reviewDuration);
    return homeworkPatchDto;
  }

}
