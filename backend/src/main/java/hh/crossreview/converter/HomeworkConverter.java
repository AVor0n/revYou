package hh.crossreview.converter;

import hh.crossreview.dto.homework.AllHomeworksWrapperDto;
import hh.crossreview.dto.homework.HomeworkAuthorDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkLectureDto;
import hh.crossreview.dto.homework.PostHomeworkResponseDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewDuration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class HomeworkConverter {

  public AllHomeworksWrapperDto homeworksToGetAllHomeworksWrapper(List<Homework> homeworks) {
    List<HomeworkDto> homeworkDtoList = homeworks.stream()
        .map(this::homeworkToHomeworkDto)
        .toList();
    return new AllHomeworksWrapperDto(homeworkDtoList);
  }

  public HomeworkDto homeworkToHomeworkDto(Homework homework) {

    List<String> studyDirections = homeworkToListStudyDirections(homework);
    HomeworkAuthorDto homeworkAuthorDto = homeworkToHomeworkAuthorDto(homework);
    HomeworkLectureDto homeworkLectureDto = homeworkToHomeworkLectureDto(homework);

    return new HomeworkDto()
        .setId(homework.getHomeworkId())
        .setName(homework.getName())
        .setTopic(homework.getTopic())
        .setDescription(homework.getDescription())
        .setDepartments(studyDirections)
        .setAuthor(homeworkAuthorDto)
        .setLecture(homeworkLectureDto)
        .setRepositoryLink(homework.getRepositoryLink())
        .setStartDate(homework.getStartTimestamp())
        .setCompletionDeadline(homework.getCompletionDeadline())
        .setReviewDuration(homework.getReviewDuration().getHours());
  }


  private List<String> homeworkToListStudyDirections(Homework homework) {
    return homework.getLecture()
        .getCohorts()
        .stream()
        .map(cohort -> cohort.getStudyDirection().toString())
        .toList();
  }

  private HomeworkAuthorDto homeworkToHomeworkAuthorDto(Homework homework) {
    User user = homework.getLecture().getTeacher();

    return new HomeworkAuthorDto(
        user.getUserId(),
        user.getName(),
        user.getSurname()
    );
  }

  private HomeworkLectureDto homeworkToHomeworkLectureDto(Homework homework) {
    Lecture lecture = homework.getLecture();

    return new HomeworkLectureDto(
        lecture.getLectureId(),
        lecture.getTitle()
    );
  }

  public Homework homeworkDtoToHomework(HomeworkDto homeworkDto, Lecture lecture) {
    return new Homework()
        .setStartTimestamp(homeworkDto.getStartDate())
        .setTopic(homeworkDto.getTopic())
        .setName(homeworkDto.getName())
        .setRepositoryLink(homeworkDto.getRepositoryLink())
        .setCompletionDeadline(homeworkDto.getCompletionDeadline())
        .setReviewDuration(ReviewDuration.ofHours(homeworkDto.getReviewDuration()))
        .setLecture(lecture)
        .setDescription(homeworkDto.getDescription());
  }

  public PostHomeworkResponseDto createPostHomeworkResponseDto(Integer homeworkId) {
    return new PostHomeworkResponseDto(homeworkId);
  }

  public void updateHomeworkFromHomeworkDto(Homework homework, HomeworkDto homeworkDto) {
    if (homeworkDto.getName() != null) {
      homework.setName(homeworkDto.getName());
    }
    if (homeworkDto.getTopic() != null) {
      homework.setTopic(homeworkDto.getTopic());
    }
    if (homeworkDto.getDescription() != null) {
      homework.setDescription(homeworkDto.getDescription());
    }
    if (homeworkDto.getRepositoryLink() != null) {
      homework.setRepositoryLink(homeworkDto.getRepositoryLink());
    }
    if (homeworkDto.getStartDate() != null) {
      homework.setStartTimestamp(homeworkDto.getStartDate());
    }
    if (homeworkDto.getCompletionDeadline() != null) {
      homework.setCompletionDeadline(homeworkDto.getCompletionDeadline());
    }
    if (homeworkDto.getReviewDuration() != null) {
      homework.setReviewDuration(ReviewDuration.ofHours(homeworkDto.getReviewDuration()));
    }
  }

}
