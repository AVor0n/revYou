package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "Review")
public class ReviewDto {
  private Integer reviewId;

  private String status;

  private Integer projectId;

  private String sourceCommitId;

  private String commitId;


  private List<ReviewAttemptDto> reviewAttempts;


  public Integer getReviewId() {
    return reviewId;
  }

  public ReviewDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public ReviewDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public List<ReviewAttemptDto> getReviewAttempts() {
    return reviewAttempts;
  }

  public ReviewDto setReviewAttempts(List<ReviewAttemptDto> reviewAttempts) {
    this.reviewAttempts = reviewAttempts;
    return this;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public ReviewDto setProjectId(Integer projectId) {
    this.projectId = projectId;
    return this;
  }

  public String getSourceCommitId() {
    return sourceCommitId;
  }

  public ReviewDto setSourceCommitId(String sourceCommitId) {
    this.sourceCommitId = sourceCommitId;
    return this;
  }

  public String getCommitId() {
    return commitId;
  }

  public ReviewDto setCommitId(String commitId) {
    this.commitId = commitId;
    return this;
  }
}
