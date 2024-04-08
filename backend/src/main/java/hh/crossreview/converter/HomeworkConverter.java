package hh.crossreview.converter;

import hh.crossreview.dto.homework.HomeworkAuthorDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkLectureDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
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

  public HomeworksWrapperDto convertToHomeworksWrapperDto(List<Homework> homeworks) {
    List<HomeworkDto> homeworkDtoList = homeworks
        .stream()
        .map(this::convertToHomeworkDto)
        .toList();
    return new HomeworksWrapperDto(homeworkDtoList);
  }

  public HomeworkDto convertToHomeworkDto(Homework homework) {
    List<String> studyDirections = convertToStudyDirections(homework);
    HomeworkAuthorDto homeworkAuthorDto = convertToHomeworkAuthorDto(homework);
    HomeworkLectureDto homeworkLectureDto = convertToHomeworkLectureDto(homework);

    return new HomeworkDto()
        .setId(homework.getHomeworkId())
        .setName(homework.getName())
        .setTopic(homework.getTopic())
        .setDescription(homework.getDescription())
        .setDepartments(studyDirections)
        .setAuthor(homeworkAuthorDto)
        .setLecture(homeworkLectureDto)
        .setRepositoryLink(homework.getRepositoryLink())
        .setStartDate(homework.getStartDate())
        .setCompletionDeadline(homework.getCompletionDeadline())
        .setReviewDuration(homework.getReviewDuration().getHours());
  }


  private List<String> convertToStudyDirections(Homework homework) {
    return homework
        .getLecture()
        .getCohorts()
        .stream()
        .map(cohort -> cohort.getStudyDirection().toString())
        .toList();
  }

  private HomeworkAuthorDto convertToHomeworkAuthorDto(Homework homework) {
    User user = homework.getAuthor();

    return new HomeworkAuthorDto(
        user.getUserId(),
        user.getName(),
        user.getSurname()
    );
  }

  private HomeworkLectureDto convertToHomeworkLectureDto(Homework homework) {
    Lecture lecture = homework.getLecture();

    return new HomeworkLectureDto(
        lecture.getLectureId(),
        lecture.getName()
    );
  }

  public Homework convertToHomework(HomeworkDto homeworkDto, Lecture lecture, User author) {
    return new Homework()
        .setStartDate(homeworkDto.getStartDate())
        .setTopic(homeworkDto.getTopic())
        .setName(homeworkDto.getName())
        .setRepositoryLink(homeworkDto.getRepositoryLink())
        .setCompletionDeadline(homeworkDto.getCompletionDeadline())
        .setReviewDuration(ReviewDuration.ofHours(homeworkDto.getReviewDuration()))
        .setLecture(lecture)
        .setDescription(homeworkDto.getDescription())
        .setAuthor(author);
  }

  public HomeworkPostResponseDto convertToHomeworkPostResponseDto(Integer homeworkId) {
    return new HomeworkPostResponseDto(homeworkId);
  }

  public void merge(Homework homework, HomeworkDto homeworkDto) {
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
      homework.setStartDate(homeworkDto.getStartDate());
    }
    if (homeworkDto.getCompletionDeadline() != null) {
      homework.setCompletionDeadline(homeworkDto.getCompletionDeadline());
    }
    if (homeworkDto.getReviewDuration() != null) {
      homework.setReviewDuration(ReviewDuration.ofHours(homeworkDto.getReviewDuration()));
    }
  }

}
