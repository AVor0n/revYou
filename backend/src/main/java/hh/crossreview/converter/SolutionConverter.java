package hh.crossreview.converter;

import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.external.gitlab.entity.ParsedGitlabLink;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Named
@Singleton
public class SolutionConverter {

  public Solution convertToSolution(
      ParsedGitlabLink parsedGitlabLink,
      Homework homework,
      User student
  ) {
    return new Solution()
        .setBranchLink(parsedGitlabLink.branchLink())
        .setStatus(SolutionStatus.IN_PROGRESS)
        .setApproveScore(0)
        .setReviewScore(0)
        .setRepository(parsedGitlabLink.repository())
        .setBranch(parsedGitlabLink.branch())
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
        .setStatus(solution.getStatus().toString())
        .setRepository(solution.getRepository())
        .setBranch(solution.getBranch())
        .setApproveScore(solution.getApproveScore())
        .setReviewScore(solution.getReviewScore())
        .setBranchLink(solution.getBranchLink())
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

  public void merge(Solution solution, ParsedGitlabLink parsedGitlabLink) {
    if (parsedGitlabLink.branchLink() != null) {
      solution.setBranchLink(parsedGitlabLink.branchLink());
    }
    if (parsedGitlabLink.branch() != null) {
      solution.setBranch(parsedGitlabLink.branch());
    }
    if (parsedGitlabLink.repository() != null) {
      solution.setRepository(parsedGitlabLink.repository());
    }
  }

}
