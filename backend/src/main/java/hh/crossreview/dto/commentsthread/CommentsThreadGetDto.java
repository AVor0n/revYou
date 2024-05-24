package hh.crossreview.dto.commentsthread;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ThreadGet")
public class CommentsThreadGetDto {
  private Integer reviewId;

  public Integer getReviewId() {
    return reviewId;
  }

  public CommentsThreadGetDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }
}
