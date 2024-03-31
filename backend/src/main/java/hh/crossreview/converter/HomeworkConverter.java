package hh.crossreview.converter;

import hh.crossreview.dto.homework.GetAllHomeworksWrapper;
import hh.crossreview.dto.homework.GetHomeworkAuthorDto;
import hh.crossreview.dto.homework.GetHomeworkDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class HomeworkConverter {

  public GetAllHomeworksWrapper homeworksToGetAllHomeworksWrapper(List<Homework> homeworks) {
    List<GetHomeworkDto> getHomeworkDtoList = homeworks.stream()
        .map(this::homeworkToGetHomeworkDto)
        .toList();
    return new GetAllHomeworksWrapper(getHomeworkDtoList);
  }

  public GetHomeworkDto homeworkToGetHomeworkDto(Homework homework) {

    List<String> studyDirections = homeworkToListStudyDirections(homework);
    GetHomeworkAuthorDto getHomeworkAuthorDto = homeworkToGetHomeworkAuthorDto(homework);

    return new GetHomeworkDto()
        .setHomeworkId(homework.getHomeworkId())
        .setTitle(homework.getTitle())
        .setTheme(homework.getTheme())
        .setDescription(homework.getDescription())
        .setDepartments(studyDirections)
        .setAuthor(getHomeworkAuthorDto)
        .setHomeworkLink(homework.getHomeworkLink())
        .setCreationDate(homework.getCreationTimestamp())
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

}
