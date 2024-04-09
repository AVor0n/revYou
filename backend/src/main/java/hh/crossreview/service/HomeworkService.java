package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPatchDto;
import hh.crossreview.dto.homework.HomeworkPostDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@Singleton
public class HomeworkService extends GenericService {

  private final HomeworkDao homeworkDao;
  private final HomeworkConverter homeworkConverter;

  public HomeworkService(
      HomeworkDao homeworkDao,
      UserDao userDao,
      HomeworkConverter homeworkConverter) {
    super(userDao);
    this.homeworkDao = homeworkDao;
    this.homeworkConverter = homeworkConverter;
  }

  @Transactional
  public HomeworksWrapperDto getHomeworks(UsernamePasswordAuthenticationToken token) {
    User user = retrieveUserFromToken(token);
    List<Homework> homeworks;
    switch(user.getRole().toString()) {
      case "ADMIN" -> homeworks = homeworkDao.findAll();
      case "TEACHER" -> homeworks = homeworkDao.findByAuthor(user);
      case "STUDENT" -> homeworks = homeworkDao.findByCohort(user.getCohort());
      default -> throw new ServerErrorException("Unexpected user role", Response.Status.INTERNAL_SERVER_ERROR);
    }
    return homeworkConverter.convertToHomeworksWrapperDto(homeworks);
  }

  @Transactional
  public HomeworkPostResponseDto createHomework(HomeworkPostDto homeworkPostDto, UsernamePasswordAuthenticationToken token) {
    Integer lectureId = homeworkPostDto.getLectureId();
    Lecture lecture = homeworkDao.find(Lecture.class, lectureId);
    requireEntityNotNull(lecture, String.format("Lecture with id %d was not found", lectureId));
    requireAuthorPermission(token, lecture);

    User user = retrieveUserFromToken(token);
    Homework homework = homeworkConverter.convertToHomework(homeworkPostDto, lecture, user);
    homeworkDao.save(homework);
    return homeworkConverter.convertToHomeworkPostResponseDto(homework.getHomeworkId());
  }

  @Transactional
  public HomeworkDto getHomework(Integer homeworkId) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    return homeworkConverter.convertToHomeworkDto(homework);
  }


  @Transactional
  public void deleteHomework(Integer homeworkId, UsernamePasswordAuthenticationToken token) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    requireAuthorPermission(token, homework);
    homeworkDao.deleteHomework(homework);
  }

  @Transactional
  public void updateHomework(
      Integer homeworkId,
      HomeworkPatchDto homeworkPatchDto,
      UsernamePasswordAuthenticationToken token
  ) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, getHomeworkNotFoundMessage(homeworkId));
    requireAuthorPermission(token, homework);
    homeworkConverter.merge(homework, homeworkPatchDto);
    homeworkDao.save(homework);
  }

  private String getHomeworkNotFoundMessage(Integer homeworkId) {
    return String.format("Homework with id %d was not found", homeworkId);
  }

}
