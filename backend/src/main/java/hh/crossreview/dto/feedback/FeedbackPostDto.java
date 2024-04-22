package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HomeworkPost")
public class FeedbackPostDto {

    return reviewer;
}

  public void setReviewer(Integer reviewer) {
    this.reviewer = reviewer;
  }

  public Integer getStudent() {
    return student;
  }

  public void setStudent(Integer student) {
    this.student = student;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer reviewer;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer student;
  private String description;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer rating;
}

  public Integer getReviewer() {