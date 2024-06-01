package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewerChange", requiredProperties = {
    "reviewId", "reviewerId"
})
@SuppressWarnings({"unused"})
public class ReviewerChangeDto {

  private Integer reviewId;
  private Integer reviewerId;

  public Integer getReviewId() {
    return reviewId;
  }

  public Integer getReviewerId() {
    return reviewerId;
  }

  public ReviewerChangeDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public ReviewerChangeDto setReviewerId(Integer reviewerId) {
    this.reviewerId = reviewerId;
    return this;
  }

}
