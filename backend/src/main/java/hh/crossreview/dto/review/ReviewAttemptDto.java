package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Schema(name = "ReviewAttempt", requiredProperties = {
    "reviewAttemptId", "reviewId", "solutionAttemptId", "createdAt"
})
@SuppressWarnings({"unused"})
public class ReviewAttemptDto {
  private Integer reviewAttemptId;

  private Integer reviewId;

  private Integer solutionAttemptId;

  private OffsetDateTime createdAt;

  private OffsetDateTime finishedAt;

  private String resolution;

  public Integer getReviewAttemptId() {
    return reviewAttemptId;
  }


  public ReviewAttemptDto setReviewAttemptId(Integer reviewAttemptId) {
    this.reviewAttemptId = reviewAttemptId;
    return this;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public ReviewAttemptDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public Integer getSolutionAttemptId() {
    return solutionAttemptId;
  }

  public ReviewAttemptDto setSolutionAttemptId(Integer solutionAttemptId) {
    this.solutionAttemptId = solutionAttemptId;
    return this;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public ReviewAttemptDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt.atOffset(ZoneOffset.UTC);
    return this;
  }

  public OffsetDateTime getFinishedAt() {
    return finishedAt;
  }

  public ReviewAttemptDto setFinishedAt(LocalDateTime finishedAt) {
    this.finishedAt = finishedAt != null ? finishedAt.atOffset(ZoneOffset.UTC) : null;
    return this;
  }

  public String getResolution() {
    return resolution;
  }

  public ReviewAttemptDto setResolution(String resolution) {
    this.resolution = resolution;
    return this;
  }
}

