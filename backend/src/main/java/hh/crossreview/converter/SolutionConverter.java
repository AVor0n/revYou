package hh.crossreview.converter;

import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.external.gitlab.entity.RepositoryInfo;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Named
@Singleton
public class SolutionConverter {

  public Solution convertToSolution(
      RepositoryInfo parsedGitlabLink,
      Homework homework,
      User student
  ) {
    return new Solution()
        .setStatus(SolutionStatus.IN_PROGRESS)
        .setApproveScore(0)
        .setReviewScore(0)
        .setProjectId(parsedGitlabLink.getProjectId())
        .setBranch(parsedGitlabLink.getBranch())
        .setSourceCommitId(homework.getSourceCommitId())
        .setHomework(homework)
        .setStudent(student)
        .setSolutionAttempts(new ArrayList<>());
  }

  public SolutionDto convertToSolutionDto(Solution solution) {
    List<SolutionAttemptDto> solutionAttemptsDto = solution
        .getSolutionAttempts()
        .stream()
        .map(this::convertToSolutionAttemptDto)
        .toList();
    return new SolutionDto()
        .setStatus(solution.getStatus())
        .setProjectId(solution.getProjectId())
        .setBranch(solution.getBranch())
        .setSourceCommitId(solution.getSourceCommitId())
        .setApproveScore(solution.getApproveScore())
        .setReviewScore(solution.getReviewScore())
        .setStudentId(solution.getAuthorId())
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

  public void merge(Solution solution, RepositoryInfo parsedGitlabLink) {
    if (parsedGitlabLink.getBranch() != null) {
      solution.setBranch(parsedGitlabLink.getBranch());
    }
    if (parsedGitlabLink.getProjectId() != null) {
      solution.setProjectId(parsedGitlabLink.getProjectId());
    }
  }

}
