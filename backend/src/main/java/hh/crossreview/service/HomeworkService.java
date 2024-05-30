package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPatchDto;
import hh.crossreview.dto.homework.HomeworkPostDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@Singleton
public class HomeworkService {

  private final HomeworkDao homeworkDao;
  private final HomeworkConverter homeworkConverter;
  private final RequirementsUtils reqUtils;
  private final SolutionDao solutionDao;

  public HomeworkService(
      HomeworkDao homeworkDao,
      HomeworkConverter homeworkConverter,
      RequirementsUtils reqUtils,
      SolutionDao solutionDao
  ) {
    this.homeworkDao = homeworkDao;
    this.homeworkConverter = homeworkConverter;
    this.reqUtils = reqUtils;
    this.solutionDao = solutionDao;
  }

  @Transactional
  public HomeworksWrapperDto getHomeworks(User user) {
    List<Homework> homeworks;
    List<SolutionStatus> solutionStatuses = new ArrayList<>();
    switch (user.getRole().toString()) {
      case "ADMIN" -> homeworks = homeworkDao.findAll();
      case "TEACHER" -> homeworks = homeworkDao.findByAuthor(user);
      case "STUDENT" -> {
        homeworks = homeworkDao.findByCohort(user.getCohort());
        homeworks.forEach(homework -> {
          Optional<Solution> solution = solutionDao.findByHomeworkAndStudent(homework, user);
          solutionStatuses.add(solution.map(Solution::getStatus).orElse(null));
        });
      }
      default -> throw new ServerErrorException("Unexpected user role", Response.Status.INTERNAL_SERVER_ERROR);
    }
    return homeworkConverter.convertToHomeworksWrapperDto(homeworks, solutionStatuses);
  }

  @Transactional
  public HomeworkPostResponseDto createHomework(
      HomeworkPostDto homeworkPostDto,
      User user,
      String commitId
  ) {
    Integer lectureId = homeworkPostDto.getLectureId();
    Lecture lecture = homeworkDao.find(Lecture.class, lectureId);
    reqUtils.requireEntityNotNull(lecture, String.format("Lecture with id %d was not found", lectureId));
    reqUtils.requireAuthorPermissionOrAdmin(user, lecture);

    Homework homework = homeworkConverter.convertToHomework(homeworkPostDto, lecture, commitId);
    homeworkDao.save(homework);
    return homeworkConverter.convertToHomeworkPostResponseDto(homework.getHomeworkId());
  }

  @Transactional
  public HomeworkDto getHomework(Integer homeworkId, User user) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    reqUtils.requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    Optional<Solution> solution = solutionDao.findByHomeworkAndStudent(homework, user);
    SolutionStatus status = solution.map(Solution::getStatus).orElse(null);
    return homeworkConverter.convertToHomeworkDto(homework, status);
  }


  @Transactional
  public void deleteHomework(Integer homeworkId, User user) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    reqUtils.requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    reqUtils.requireAuthorPermissionOrAdmin(user, homework);
    homeworkDao.deleteHomework(homework);
  }

  @Transactional
  public void updateHomework(
      Integer homeworkId,
      HomeworkPatchDto homeworkPatchDto,
      User user
  ) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    reqUtils.requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    reqUtils.requireAuthorPermissionOrAdmin(user, homework);
    homeworkConverter.merge(homework, homeworkPatchDto);
    homeworkDao.save(homework);
  }

  private String getHomeworkNotFoundMessage(Integer homeworkId) {
    return String.format("Homework with id %d was not found", homeworkId);
  }

  public Homework getHomeworkEntity(Integer homeworkId) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    reqUtils.requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    return homework;
  }

}
