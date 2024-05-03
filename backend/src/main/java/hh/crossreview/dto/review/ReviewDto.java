package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "Review")
public class ReviewDto {
  private Integer reviewId;

  private Integer studentId;

  private Integer reviewerId;

  private String status;

  private Integer solutionId;

  private List<ReviewAttemptDto> reviewAttempts;


  public Integer getReviewId() {
    return reviewId;
  }

  public ReviewDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public ReviewDto setStudentId(Integer studentId) {
    this.studentId = studentId;
    return this;
  }

  public Integer getReviewerId() {
    return reviewerId;
  }

  public ReviewDto setReviewerId(Integer reviewerId) {
    this.reviewerId = reviewerId;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public ReviewDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public Integer getSolutionId() {
    return solutionId;
  }

  public ReviewDto setSolutionId(Integer solutionId) {
    this.solutionId = solutionId;
    return this;
  }

  public List<ReviewAttemptDto> getReviewAttempts() {
    return reviewAttempts;
  }

  public ReviewDto setReviewAttempts(List<ReviewAttemptDto> reviewAttempts) {
    this.reviewAttempts = reviewAttempts;
    return this;
  }
}
