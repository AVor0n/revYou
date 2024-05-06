package hh.crossreview.dto.solution;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
    name = "Solution",
    requiredProperties = {
        "status", "projectId", "branch",
        "sourceCommitId", "approveScore", "reviewScore",
        "studentId", "solutionAttempts"
    }
)
@SuppressWarnings({"unused"})
public class SolutionDto {

  private String status;

  private Integer projectId;

  private String branch;

  private String sourceCommitId;

  private Integer approveScore;

  private Integer reviewScore;

  private Integer studentId;

  private List<SolutionAttemptDto> solutionAttempts;

  public String getStatus() {
    return status;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public String getBranch() {
    return branch;
  }

  public String getSourceCommitId() {
    return sourceCommitId;
  }

  public Integer getApproveScore() {
    return approveScore;
  }

  public Integer getReviewScore() {
    return reviewScore;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public List<SolutionAttemptDto> getSolutionAttempts() {
    return solutionAttempts;
  }

  public SolutionDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public SolutionDto setProjectId(Integer projectId) {
    this.projectId = projectId;
    return this;
  }

  public SolutionDto setBranch(String branch) {
    this.branch = branch;
    return this;
  }

  public SolutionDto setSourceCommitId(String sourceCommitId) {
    this.sourceCommitId = sourceCommitId;
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

  public SolutionDto setStudentId(Integer studentId) {
    this.studentId = studentId;
    return this;
  }

  public SolutionDto setSolutionAttempts(List<SolutionAttemptDto> solutionAttempts) {
    this.solutionAttempts = solutionAttempts;
    return this;
  }
}
