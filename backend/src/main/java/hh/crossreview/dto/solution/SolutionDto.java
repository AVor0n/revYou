package hh.crossreview.dto.solution;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "Solution")
@SuppressWarnings({"unused"})
public class SolutionDto {

  private String status;

  private Integer approveScore;

  private Integer reviewScore;

  private String branchLink;

  private List<SolutionAttemptDto> solutionAttempts;

  public String getStatus() {
    return status;
  }

  public Integer getApproveScore() {
    return approveScore;
  }

  public Integer getReviewScore() {
    return reviewScore;
  }

  public String getBranchLink() {
    return branchLink;
  }

  public List<SolutionAttemptDto> getSolutionAttempts() {
    return solutionAttempts;
  }

  public SolutionDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public SolutionDto setApproveScore(Integer approveScore) {
    this.approveScore = approveScore;
    return this;
  }

  public SolutionDto setReviewScore(Integer reviewScore) {
    this.reviewScore = reviewScore;
    return this;
  }

  public SolutionDto setBranchLink(String branchLink) {
    this.branchLink = branchLink;
    return this;
  }

  public SolutionDto setSolutionAttempts(List<SolutionAttemptDto> solutionAttempts) {
    this.solutionAttempts = solutionAttempts;
    return this;
  }
}
