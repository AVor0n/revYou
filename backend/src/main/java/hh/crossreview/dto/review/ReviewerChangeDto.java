package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(name = "ReviewerChange", requiredProperties = {
    "reviewId", "reviewerId"
})
@SuppressWarnings({"unused"})
public class ReviewerChangeDto {
  @Pattern(regexp = "[0-9]+", message = "reviewId must be a number")
  private Integer reviewId;

  @Pattern(regexp = "[0-9]+", message = "reviewerId must be a number")
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
