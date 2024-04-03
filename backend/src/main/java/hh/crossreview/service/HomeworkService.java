package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import java.util.List;

@Named
@Singleton
public class HomeworkService extends GenericService {

  private final HomeworkDao homeworkDao;
  private final HomeworkConverter homeworkConverter;

  public HomeworkService(HomeworkDao homeworkDao, HomeworkConverter homeworkConverter) {
    this.homeworkDao = homeworkDao;
    this.homeworkConverter = homeworkConverter;
  }

  @Transactional
  public HomeworksWrapperDto getHomeworks() {
    List<Homework> homeworks = homeworkDao.getHomeworks();
    return homeworkConverter.convertToHomeworksWrapperDto(homeworks);
  }

  @Transactional
  public HomeworkPostResponseDto createHomework(HomeworkDto homeworkDto) {
    Integer lectureId = homeworkDto.getLecture().getId();
    Integer authorId = homeworkDto.getAuthor().getId();

    Lecture lecture = homeworkDao.find(Lecture.class, lectureId);
    requireNotNull(lecture, String.format("Lecture with id %d was not found", lectureId));

    if (!authorId.equals(lecture.getTeacher().getUserId())) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }

    Homework homework = homeworkConverter.convertToHomework(homeworkDto, lecture);
    homeworkDao.save(homework);
    return homeworkConverter.convertToHomeworkPostResponseDto(homework.getHomeworkId());
  }

  @Transactional
  public HomeworkDto getHomework(Integer homeworkId) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireNotNull(homework, String.format("Homework with id %d was not found", homeworkId));
    return homeworkConverter.convertToHomeworkDto(homework);
  }

  @Transactional
  public void deleteHomework(Integer homeworkId) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    if (homework != null) {
      homeworkDao.deleteHomework(homework);
    }
  }

  @Transactional
  public void updateHomework(Integer homeworkId, HomeworkDto homeworkDto) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    homeworkConverter.merge(homework, homeworkDto);
    homeworkDao.save(homework);
  }

}
