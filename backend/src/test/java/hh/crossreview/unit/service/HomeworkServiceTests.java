package hh.crossreview.unit.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPatchDto;
import hh.crossreview.dto.homework.HomeworkPostDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.StudyDirection;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.service.HomeworkService;
import hh.crossreview.unit.TestsUtil;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HomeworkServiceTests extends TestsUtil {

  @Mock
  private HomeworkDao homeworkDao;
  @Spy
  private HomeworkConverter homeworkConverter = new HomeworkConverter();
  @Spy
  @SuppressWarnings("unused")
  private RequirementsUtils reqUtils = new RequirementsUtils();

  @InjectMocks
  private HomeworkService homeworkService;

  @Test
  void givenGetHomeworksCall_whenStudent_thenGetStudentHomeworks() {
    Homework backHomework = createBackHomework();
    Cohort backCohort = backHomework.getCohorts().get(0);
    User student = createUser(1, UserRole.STUDENT, backCohort);

    when(homeworkDao.findByCohort(student.getCohort())).thenReturn(List.of(backHomework));
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(student);
    List<HomeworkDto> homeworksDto = homeworksWrapperDto.getData();

    assertNotNull(homeworksWrapperDto);
    assertEquals(1, homeworksWrapperDto.getData().size());
    assertEquals(backHomework.getHomeworkId(), homeworksDto.get(0).getId());
    assertEquals(backHomework.getAuthor().getUserId(), homeworksDto.get(0).getAuthor().getId());
  }

  @Test
  void givenGetHomeworksCall_whenTeacher_thenGetTeacherHomeworks() {
    Homework backHomework = createBackHomework();
    Cohort backCohort = backHomework.getCohorts().get(0);
    User teacher = createUser(1, UserRole.TEACHER, backCohort);

    when(homeworkDao.findByAuthor(teacher)).thenReturn(List.of(backHomework));
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(teacher);
    List<HomeworkDto> homeworksDto = homeworksWrapperDto.getData();

    assertNotNull(homeworksWrapperDto);
    assertEquals(1, homeworksWrapperDto.getData().size());
    assertEquals(backHomework.getHomeworkId(), homeworksDto.get(0).getId());
    assertEquals(backHomework.getAuthor().getUserId(), homeworksDto.get(0).getAuthor().getId());
  }

  @Test
  void givenGetHomeworksCall_whenAdmin_thenGetAllHomeworks() {
    Homework backHomework = createBackHomework();
    Homework frontHomework = createFrontHomework();
    List<Homework> homeworks = Arrays.asList(backHomework, frontHomework);
    homeworks = homeworks.stream().sorted(Comparator.comparingInt(Homework::getHomeworkId)).toList();
    User admin = createUser(1, UserRole.ADMIN, null);

    when(homeworkDao.findAll()).thenReturn(homeworks);
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(admin);
    List<HomeworkDto> homeworksDto = homeworksWrapperDto
        .getData()
        .stream()
        .sorted(Comparator.comparingInt(HomeworkDto::getId))
        .toList();

    assertNotNull(homeworksWrapperDto);
    assertEquals(2, homeworksWrapperDto.getData().size());
    assertEquals(homeworks.get(0).getHomeworkId(), homeworksDto.get(0).getId());
    assertEquals(homeworks.get(0).getAuthor().getUserId(), homeworksDto.get(0).getAuthor().getId());
  }

  @Test
  void givenCreateHomeworkCall_whenAuthor_thenSuccessfullyCreate() {
    HomeworkPostDto homeworkPostDto = createHomeworkPostDto(1);
    Cohort cohort = createCohort(StudyDirection.BACK);
    User lector = createUser(1, UserRole.TEACHER, cohort);
    Lecture lecture = createLecture(1, List.of(cohort), lector);
    String sourceCommitId = "abcdefabcdefabcd";

    when(homeworkDao.find(Lecture.class, 1)).thenReturn(lecture);
    doNothing().when(homeworkDao).save(any());
    HomeworkPostResponseDto homeworkPostResponseDto = homeworkService.createHomework(homeworkPostDto, lector, sourceCommitId);

    verify(homeworkConverter).convertToHomework(homeworkPostDto, lecture, sourceCommitId);
    assertNotNull(homeworkPostResponseDto);
  }

  @Test
  void givenCreateHomeworkCall_whenNotAuthor_thenNotFoundErrorThrow() {
    User teacher = createUser(1, UserRole.TEACHER, new Cohort());
    HomeworkPostDto homeworkPostDto = new HomeworkPostDto();
    String sourceCommitId = "abcdefabcdefabcd";

    assertThrows(
        NotFoundException.class,
        () -> homeworkService.createHomework(homeworkPostDto, teacher, sourceCommitId)
    );
  }

  @Test
  void givenCreateHomeworkCall_whenAdmin_thenSuccessfullyCreate() {
    User admin = createUser(1, UserRole.ADMIN, new Cohort());
    Lecture lecture = createLecture(1, Collections.emptyList(), new User());
    String sourceCommitId = "abcdefabcdefabcd";

    when(homeworkDao.find(Lecture.class, 1)).thenReturn(lecture);
    doNothing().when(homeworkDao).save(any());
    HomeworkPostResponseDto homeworkPostResponseDto = homeworkService.createHomework(
        createHomeworkPostDto(1),
        admin,
        sourceCommitId
    );

    assertNotNull(homeworkPostResponseDto);
  }

  @Test
  void givenGetHomeworkCall_whenInvalidHomeworkId_thenNotFoundErrorThrow() {
    when(homeworkDao.find(Homework.class, 1)).thenReturn(null);

    assertThrows(
        NotFoundException.class,
        () -> {
          User user = createUser(1, UserRole.TEACHER, new Cohort());
          homeworkService.getHomework(1, user);
        }
    );
  }

  @Test
  void givenDeleteHomeworkCall_whenInvalidHomeworkId_thenNotFoundErrorThrow() {
    User teacher = createUser(1, UserRole.TEACHER, new Cohort());
    when(homeworkDao.find(Homework.class, 1)).thenReturn(null);

    assertThrows(
        NotFoundException.class,
        () -> homeworkService.deleteHomework(1, teacher)
    );
  }

  @Test
  void givenUpdateHomeworkCall_whenValid_thenSuccessfullyUpdate() {
    Homework homework = createBackHomework();
    HomeworkPatchDto homeworkPatchDto = createHomeworkPatchDto(
        "NewDescription",
        "NewTopic",
        "NewName",
        24,
        Date.from(Instant.parse("2024-04-25T00:00:00Z")),
        Date.from(Instant.parse("2024-04-30T00:00:00Z"))
    );

    when(homeworkDao.find(Homework.class, 1)).thenReturn(homework);
    doNothing().when(homeworkDao).save(any(Homework.class));
    homeworkService.updateHomework(1, homeworkPatchDto, homework.getAuthor());

    assertEquals(homework.getName(), homeworkPatchDto.getName());
    assertEquals(homework.getDescription(), homeworkPatchDto.getDescription());
    assertEquals(homework.getTopic(), homeworkPatchDto.getTopic());
    assertEquals(homework.getStartDate(), homeworkPatchDto.getStartDate());
    assertEquals(homework.getCompletionDeadline(), homeworkPatchDto.getCompletionDeadline());
    assertEquals(homework.getReviewDuration().getHours(), homeworkPatchDto.getReviewDuration());
  }

  @SuppressWarnings("SameParameterValue")
  private HomeworkPostDto createHomeworkPostDto(Integer lectureId) {
    HomeworkPostDto homeworkPostDto = new HomeworkPostDto();
    homeworkPostDto.setLectureId(lectureId);
    homeworkPostDto.setReviewDuration(48);
    return homeworkPostDto;
  }

  @SuppressWarnings("SameParameterValue")
  private HomeworkPatchDto createHomeworkPatchDto(
      String description,
      String topic,
      String name,
      Integer reviewDuration,
      Date startDate,
      Date completionDeadline
  ) {
    HomeworkPatchDto homeworkPatchDto = new HomeworkPatchDto();
    homeworkPatchDto.setDescription(description);
    homeworkPatchDto.setTopic(topic);
    homeworkPatchDto.setName(name);
    homeworkPatchDto.setReviewDuration(reviewDuration);
    homeworkPatchDto.setStartDate(startDate);
    homeworkPatchDto.setCompletionDeadline(completionDeadline);
    return homeworkPatchDto;
  }

}
