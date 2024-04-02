package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.AllHomeworksWrapperDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.PostHomeworkResponseDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ForbiddenException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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
  public AllHomeworksWrapperDto getHomeworks() {
    List<Homework> homeworks = homeworkDao.getHomeworks();
    return homeworkConverter.homeworksToGetAllHomeworksWrapper(homeworks);
  }

  @Transactional
  public PostHomeworkResponseDto createHomework(HomeworkDto homeworkDto) {
    Integer lectureId = homeworkDto.getLecture().getId();
    Integer authorId = homeworkDto.getAuthor().getId();

    Lecture lecture = homeworkDao.find(Lecture.class, lectureId);
    checkEntityNotNull(lecture, String.format("Lecture with id %d was not found", lectureId));

    if (!authorId.equals(lecture.getTeacher().getUserId())) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }

    Homework homework = homeworkConverter.homeworkDtoToHomework(homeworkDto, lecture);
    homeworkDao.createHomework(homework);
    return homeworkConverter.createPostHomeworkResponseDto(homework.getHomeworkId());
  }

}
