package hh.crossreview.converter;

import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.time.LocalDateTime;

@Named
@Singleton
public class SolutionConverter {

  public Solution convertToSolution(
      SolutionPostDto solutionPostDto,
      Homework homework,
      User author
  ) {
    return new Solution()
        .setBranchLink(solutionPostDto.getBranchLink())
        .setCommitId(solutionPostDto.getCommitId())
        .setAttemptNumber(solutionPostDto.getAttemptNumber())
        .setCreationTimestamp(LocalDateTime.now())
        .setHomework(homework)
        .setAuthor(author);
  }

  public SolutionDto convertToSolutionDto(Solution solution, HomeworkDto homeworkDto) {
    return new SolutionDto()
        .setId(solution.getSolutionId())
        .setAttemptNumber(solution.getAttemptNumber())
        .setHomework(homeworkDto)
        .setBranchLink(solution.getBranchLink())
        .setCreationTimestamp(solution.getCreationTimestamp())
        .setCommitId(solution.getCommitId());
  }

}
