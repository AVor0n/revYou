package hh.crossreview.dto.review.info;

import hh.crossreview.dto.review.ReviewAttemptDto;
import hh.crossreview.dto.user.info.StudentDto;
import hh.crossreview.entity.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.util.List;

@Schema(name = "ReviewInfo", requiredProperties = {
    "reviewId", "projectId", "commitId",
    "status", "duration", "student",
    "reviewAttempts"})
@SuppressWarnings({"unused"})
public class ReviewInfoDto {

  private Integer reviewId;
  private Integer projectId;
  private String commitId;
  @Schema(enumAsRef = true)
  private ReviewStatus status;
  private ReviewDurationDto duration;
  private StudentDto student;
  private StudentDto reviewer;
  private List<ReviewAttemptDto> reviewAttempts;

  public ReviewInfoDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public ReviewInfoDto setProjectId(Integer projectId) {
    this.projectId = projectId;
    return this;
  }

  public ReviewInfoDto setCommitId(String commitId) {
    this.commitId = commitId;
    return this;
  }

  public ReviewInfoDto setStatus(ReviewStatus status) {
    this.status = status;
    return this;
  }

  public ReviewInfoDto setDuration(Duration duration) {
    this.duration = new ReviewDurationDto(duration);
    return this;
  }

  public ReviewInfoDto setStudent(StudentDto student) {
    this.student = student;
    return this;
  }

  public ReviewInfoDto setReviewer(StudentDto reviewer) {
    this.reviewer = reviewer;
    return this;
  }

  public ReviewInfoDto setReviewAttempts(List<ReviewAttemptDto> reviewAttempts) {
    this.reviewAttempts = reviewAttempts;
    return this;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public String getCommitId() {
    return commitId;
  }

  public ReviewStatus getStatus() {
    return status;
  }

  public ReviewDurationDto getDuration() {
    return duration;
  }

  public StudentDto getStudent() {
    return student;
  }

  public StudentDto getReviewer() {
    return reviewer;
  }

  public List<ReviewAttemptDto> getReviewAttempts() {
    return reviewAttempts;
  }

}
