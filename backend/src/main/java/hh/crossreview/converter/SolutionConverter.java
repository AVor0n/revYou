package hh.crossreview.converter;

import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.SolutionStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class SolutionConverter {

  public Solution convertToSolution(
      SolutionPostDto solutionPostDto,
      Homework homework,
      User student
  ) {
    return new Solution()
        .setBranchLink(solutionPostDto.getBranchLink())
        .setStatus(SolutionStatus.ATTEMPTED)
        .setApproveScore(0)
        .setReviewScore(0)
        .setHomework(homework)
        .setStudent(student);
  }

  public SolutionDto convertToSolutionDto(Solution solution) {
    List<SolutionAttemptDto> solutionAttemptsDto = solution.getSolutionAttempts()
        .stream()
        .map(this::convertToSolutionAttemptDto)
        .toList();
    return new SolutionDto()
        .setStatus(solution.getStatus().toString())
        .setApproveScore(solution.getApproveScore())
        .setReviewScore(solution.getReviewScore())
        .setBranchLink(solution.getBranchLink())
        .setSolutionAttempts(solutionAttemptsDto);
  }

  public SolutionAttemptDto convertToSolutionAttemptDto(SolutionAttempt solutionAttempt) {
    return new SolutionAttemptDto(
        solutionAttempt.getCommitId(),
        solutionAttempt.getCreatedAt()
    );
  }

  public SolutionsWrapperDto convertToSolutionsWrapperDto(List<Solution> solutions) {
    List<SolutionDto> solutionsDto = solutions
        .stream()
        .map(this::convertToSolutionDto)
        .toList();
    return new SolutionsWrapperDto(solutionsDto);
  }

}
