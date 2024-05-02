package hh.crossreview.dto.solution;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "SolutionAttempt", requiredProperties = {"commitId", "createdAt"})
@SuppressWarnings({"unused"})
public class SolutionAttemptDto {

  private String commitId;
  private LocalDateTime createdAt;

  public String getCommitId() {
    return commitId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public SolutionAttemptDto setCommitId(String commitId) {
    this.commitId = commitId;
    return this;
  }

  public SolutionAttemptDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public SolutionAttemptDto(String commitId, LocalDateTime createdAt) {
    this.commitId = commitId;
    this.createdAt = createdAt;
  }

}
