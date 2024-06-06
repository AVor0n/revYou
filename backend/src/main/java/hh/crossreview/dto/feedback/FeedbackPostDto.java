package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "FeedbackPost")
@SuppressWarnings({"unused"})
public class FeedbackPostDto {

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  @Pattern(regexp = "[0-9]+", message = "id must be a number")
  private Integer reviewId;
  @Pattern(regexp = "[0-5]", message = "rating must be a number between 0 and 5")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer rating;
  @NotBlank(message = "Field 'description' cannot be empty")
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

