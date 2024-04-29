package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HomeworkPost")
public class FeedbackPostDto {

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer review;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer student;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer rating;

  public Integer getReview() {
    return review;
  }

  public void setReview(Integer review) {
    this.review = review;
  }

  public Integer getStudent() {
    return student;
  }

  public void setStudent(Integer student) {
    this.student = student;
  }


  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }
}

