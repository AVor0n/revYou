package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(name = "ReviewerChange", requiredProperties = {
    "reviewId", "reviewerId"
})
@SuppressWarnings({"unused"})
public class ReviewerChangeDto {
  @Min(value = 0L, message = "The 'reviewId' value must be positive")
  private Integer reviewId;
  @Min(value = 0L, message = "The 'reviewerId' value must be positive")
  private Integer reviewerId;

  public Integer getReviewId() {
    return reviewId;
  }

  public ReviewerChangeDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  public Integer getReviewerId() {
    return reviewerId;
  }

  public ReviewerChangeDto setReviewerId(Integer reviewerId) {
    this.reviewerId = reviewerId;
    return this;
  }

}
