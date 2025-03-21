package hh.crossreview.converter;

import hh.crossreview.dto.homework.HomeworkAuthorDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkLectureDto;
import hh.crossreview.dto.homework.HomeworkPatchDto;
import hh.crossreview.dto.homework.HomeworkPostDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewDuration;
import hh.crossreview.entity.enums.SolutionStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@Singleton
public class HomeworkConverter {

  public HomeworksWrapperDto convertToHomeworksWrapperDto(List<Homework> homeworks, List<SolutionStatus> solutionStatuses) {
    List<HomeworkDto> homeworkDtos = new ArrayList<>();
    for (int i = 0; i < homeworks.size(); i++) {
      homeworkDtos.add(convertToHomeworkDto(homeworks.get(i), getSolutionStatus(solutionStatuses, i)));
    }
    return new HomeworksWrapperDto(homeworkDtos);
  }

  private static SolutionStatus getSolutionStatus(List<SolutionStatus> solutionStatuses, int i) {
    return solutionStatuses.isEmpty() ? null : solutionStatuses.get(i);
  }

  public HomeworkDto convertToHomeworkDto(Homework homework, SolutionStatus solutionStatus) {
    List<String> studyDirections = convertToStudyDirections(homework);
    HomeworkAuthorDto homeworkAuthorDto = convertToHomeworkAuthorDto(homework);
    HomeworkLectureDto homeworkLectureDto = convertToHomeworkLectureDto(homework);

    return new HomeworkDto()
        .setId(homework.getHomeworkId())
        .setName(homework.getName())
        .setTopic(homework.getTopic())
        .setDescription(homework.getDescription())
        .setSourceCommitId(homework.getSourceCommitId())
        .setDepartments(studyDirections)
        .setAuthor(homeworkAuthorDto)
        .setLecture(homeworkLectureDto)
        .setRepositoryLink(homework.getRepositoryLink())
        .setStartDate(homework.getStartDate())
        .setCompletionDeadline(homework.getCompletionDeadline())
        .setReviewDuration(homework.getReviewDuration().getHours())
        .setStatus(solutionStatus);
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

  public Homework convertToHomework(
      HomeworkPostDto homeworkPostDto,
      Lecture lecture,
      String commitId
  ) {
    return new Homework()
        .setStartDate(Date.from(homeworkPostDto.getStartDate().toInstant()))
        .setTopic(homeworkPostDto.getTopic())
        .setName(homeworkPostDto.getName())
        .setRepositoryLink(homeworkPostDto.getRepositoryLink())
        .setCompletionDeadline(Date.from(homeworkPostDto.getCompletionDeadline().toInstant()))
        .setReviewDuration(ReviewDuration.ofHours(homeworkPostDto.getReviewDuration()))
        .setLecture(lecture)
        .setDescription(homeworkPostDto.getDescription())
        .setSourceCommitId(commitId)
        .setAuthor(lecture.getLector());
  }

  public HomeworkPostResponseDto convertToHomeworkPostResponseDto(Integer homeworkId) {
    return new HomeworkPostResponseDto(homeworkId);
  }

  public void merge(Homework homework, HomeworkPatchDto homeworkPatchDto) {
    if (homeworkPatchDto.getName() != null) {
      homework.setName(homeworkPatchDto.getName());
    }
    if (homeworkPatchDto.getTopic() != null) {
      homework.setTopic(homeworkPatchDto.getTopic());
    }
    if (homeworkPatchDto.getDescription() != null) {
      homework.setDescription(homeworkPatchDto.getDescription());
    }
    if (homeworkPatchDto.getStartDate() != null) {
      homework.setStartDate(
          Date.from(homeworkPatchDto.getStartDate().toInstant())
      );
    }
    if (homeworkPatchDto.getCompletionDeadline() != null) {
      homework.setCompletionDeadline(
          Date.from(homeworkPatchDto.getCompletionDeadline().toInstant())
      );
    }
    if (homeworkPatchDto.getReviewDuration() != null) {
      homework.setReviewDuration(ReviewDuration.ofHours(homeworkPatchDto.getReviewDuration()));
    }
  }

}
