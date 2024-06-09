package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "FeedbackPost")
@SuppressWarnings({"unused"})
public class FeedbackPostDto {

  @Min(value = 0L, message = "The 'reviewId' value must be positive")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer reviewId;
  @Min(value = 0L, message = "The 'rating' value must be greater than 0")
  @Max(value = 5L, message = "The 'rating' value must be less than 5")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer rating;
  @NotBlank(message = "Field 'description' cannot be empty and whitespaces")
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

