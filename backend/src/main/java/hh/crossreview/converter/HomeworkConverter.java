package hh.crossreview.converter;

import hh.crossreview.dto.homework.GetAllHomeworksWrapperDto;
import hh.crossreview.dto.homework.GetHomeworkAuthorDto;
import hh.crossreview.dto.homework.GetHomeworkDto;
import hh.crossreview.dto.homework.PostHomeworkDto;
import hh.crossreview.dto.homework.PostHomeworkResponseDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class HomeworkConverter {

  public GetAllHomeworksWrapperDto homeworksToGetAllHomeworksWrapper(List<Homework> homeworks) {
    List<GetHomeworkDto> getHomeworkDtoList = homeworks.stream()
        .map(this::homeworkToGetHomeworkDto)
        .toList();
    return new GetAllHomeworksWrapperDto(getHomeworkDtoList);
  }

  public GetHomeworkDto homeworkToGetHomeworkDto(Homework homework) {

    List<String> studyDirections = homeworkToListStudyDirections(homework);
    GetHomeworkAuthorDto getHomeworkAuthorDto = homeworkToGetHomeworkAuthorDto(homework);

    return new GetHomeworkDto()
        .setId(homework.getHomeworkId())
        .setName(homework.getTitle())
        .setTopic(homework.getTheme())
        .setDescription(homework.getDescription())
        .setDepartments(studyDirections)
        .setAuthor(getHomeworkAuthorDto)
        .setHomeworkLink(homework.getHomeworkLink())
        .setStartDate(homework.getCreationTimestamp())
        .setCompletionDeadline(homework.getCompletionDeadline())
        .setReviewDeadline(homework.getReviewDeadline());
  }

  private List<String> homeworkToListStudyDirections(Homework homework) {
    return homework.getLecture()
        .getCohorts()
        .stream()
        .map(cohort -> cohort.getStudyDirection().toString())
        .toList();
  }

  private GetHomeworkAuthorDto homeworkToGetHomeworkAuthorDto(Homework homework) {
    User user = homework.getLecture().getTeacher();

    return new GetHomeworkAuthorDto(
        user.getUserId(),
        user.getName(),
        user.getSurname()
    );
  }

  public Homework postHomeworkDtoToHomework(PostHomeworkDto postHomeworkDto, Lecture lecture) {
    return new Homework()
        .setCreationTimestamp(postHomeworkDto.getStartDate())
        .setTheme(postHomeworkDto.getTopic())
        .setTitle(postHomeworkDto.getName())
        .setHomeworkLink(postHomeworkDto.getHomeworkLink())
        .setCompletionDeadline(postHomeworkDto.getCompletionDeadline())
        .setReviewDeadline(postHomeworkDto.getReviewDeadline())
        .setLecture(lecture)
        .setDescription(postHomeworkDto.getDescription());
  }

  public PostHomeworkResponseDto createPostHomeworkResponseDto(Integer homeworkId) {
    return new PostHomeworkResponseDto(homeworkId);
  }
}
