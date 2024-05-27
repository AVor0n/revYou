package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FeedbackPostResponseDto")
public class FeedbackPostResponseDto {
  private final Integer feedbackId;

  public FeedbackPostResponseDto(Integer feedbackId) {
    this.feedbackId = feedbackId;
  }

  public Integer getFeedbackId() {
    return feedbackId;
  }

}
