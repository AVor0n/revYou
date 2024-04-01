package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.GetAllHomeworksWrapperDto;
import hh.crossreview.dto.homework.PostHomeworkDto;
import hh.crossreview.dto.homework.PostHomeworkResponseDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
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
  public GetAllHomeworksWrapperDto getHomeworks() {
    List<Homework> homeworks = homeworkDao.getHomeworks();
    return homeworkConverter.homeworksToGetAllHomeworksWrapper(homeworks);
  }

  @Transactional
  public PostHomeworkResponseDto createHomework(PostHomeworkDto postHomeworkDto) {
    Lecture lecture = homeworkDao.get(Lecture.class, postHomeworkDto.getLectureId());
    checkEntityNotNull(lecture, String.format("Lecture with id %d was not found", postHomeworkDto.getLectureId()));

    if (!postHomeworkDto.getAuthorId().equals(lecture.getTeacher().getUserId())) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }

    if (!isPostHomeworkDtoValid(postHomeworkDto)) {
      throw new BadRequestException("Field is null");
    }

    Homework homework = homeworkConverter.postHomeworkDtoToHomework(postHomeworkDto, lecture);
    homeworkDao.createHomework(homework);
    return homeworkConverter.createPostHomeworkResponseDto(homework.getHomeworkId());
  }

  public boolean isPostHomeworkDtoValid(PostHomeworkDto postHomeworkDto) {
    return postHomeworkDto.getName() != null &&
        postHomeworkDto.getTopic() != null &&
        postHomeworkDto.getLectureId() != null &&
        postHomeworkDto.getAuthorId() != null &&
        postHomeworkDto.getStartDate() != null &&
        postHomeworkDto.getCompletionDeadline() != null &&
        postHomeworkDto.getReviewDeadline() != null;
  }
}
