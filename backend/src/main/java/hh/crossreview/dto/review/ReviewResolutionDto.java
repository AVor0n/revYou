package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ReviewResolution", requiredProperties = {"status", "resolution"})
public class ReviewResolutionDto {
  @Schema(allowableValues = {"CORRECTIONS_REQUIRED", "APPROVED"})
  private String status;
  @NotBlank(message = "Resolution cannot be empty")
  private String resolution;

  public String getStatus() {
    return status;
  }

  public ReviewResolutionDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public String getResolution() {
    return resolution;
  }

  public ReviewResolutionDto setResolution(String resolution) {
    this.resolution = resolution;
    return this;
  }
}
