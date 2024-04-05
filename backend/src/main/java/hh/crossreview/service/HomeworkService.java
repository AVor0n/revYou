package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Named
@Singleton
public class HomeworkService extends GenericService {

  private final HomeworkDao homeworkDao;
  private final UserDao userDao;
  private final HomeworkConverter homeworkConverter;

  public HomeworkService(HomeworkDao homeworkDao, UserDao userDao, HomeworkConverter homeworkConverter) {
    this.homeworkDao = homeworkDao;
    this.userDao = userDao;
    this.homeworkConverter = homeworkConverter;
  }

  @Transactional
  public HomeworksWrapperDto getHomeworks() {
    List<Homework> homeworks = homeworkDao.getHomeworks();
    return homeworkConverter.convertToHomeworksWrapperDto(homeworks);
  }

  @Transactional
  public HomeworkPostResponseDto createHomework(HomeworkDto homeworkDto, UsernamePasswordAuthenticationToken token) {
    Integer authorId = retrieveUserIdFromToken(token);
    Integer lectureId = homeworkDto.getLecture().getId();

    Lecture lecture = homeworkDao.find(Lecture.class, lectureId);
    requireEntityNotNull(lecture, String.format("Lecture with id %d was not found", lectureId));
    requireUserIdEquals(authorId, lecture.getTeacher().getUserId());

    Homework homework = homeworkConverter.convertToHomework(homeworkDto, lecture);
    homeworkDao.save(homework);
    return homeworkConverter.convertToHomeworkPostResponseDto(homework.getHomeworkId());
  }

  @Transactional
  public HomeworkDto getHomework(Integer homeworkId) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));
    return homeworkConverter.convertToHomeworkDto(homework);
  }

  @Transactional
  public void deleteHomework(Integer homeworkId, UsernamePasswordAuthenticationToken token) {
    Integer userId = retrieveUserIdFromToken(token);

    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));
    requireUserIdEquals(userId, homework.getLecture().getTeacher().getUserId());

    homeworkDao.deleteHomework(homework);
  }

  @Transactional
  public void updateHomework(
      Integer homeworkId,
      HomeworkDto homeworkDto,
      UsernamePasswordAuthenticationToken token
  ) {
    Integer userId = retrieveUserIdFromToken(token);

    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));
    requireUserIdEquals(userId, homework.getLecture().getTeacher().getUserId());

    homeworkConverter.merge(homework, homeworkDto);
    homeworkDao.save(homework);
  }

  private Integer retrieveUserIdFromToken(UsernamePasswordAuthenticationToken token) {
    String username = token.getPrincipal().toString();
    List<User> users = userDao.findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    }
    return users
        .getFirst()
        .getUserId();
  }
}
