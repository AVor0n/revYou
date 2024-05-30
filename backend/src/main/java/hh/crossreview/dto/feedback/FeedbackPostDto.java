package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FeedbackPost")
@SuppressWarnings({"unused"})
public class FeedbackPostDto {

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer reviewId;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer rating;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  public Integer getReviewId() {
    return reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

