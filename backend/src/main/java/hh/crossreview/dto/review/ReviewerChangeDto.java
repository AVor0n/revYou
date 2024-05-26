package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewAssignment", requiredProperties = {
    "reviewId", "selfAssignment"
})
@SuppressWarnings({"unused"})
public class ReviewerChangeDto {

  private Integer reviewId;
  private Integer reviewerId;
  private Boolean selfAssignment;

  public Integer getReviewId() {
    return reviewId;
  }

  public Integer getReviewerId() {
    return reviewerId;
  }

  public Boolean getSelfAssignment() {
    return selfAssignment;
  }

  public ReviewerChangeDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public ReviewerChangeDto setReviewerId(Integer reviewerId) {
    this.reviewerId = reviewerId;
    return this;
  }

  public ReviewerChangeDto setSelfAssignment(Boolean selfAssignment) {
    this.selfAssignment = selfAssignment;
    return this;
  }

}
