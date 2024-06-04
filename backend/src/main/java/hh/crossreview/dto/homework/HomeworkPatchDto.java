package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Schema(name = "HomeworkPatch")
public class HomeworkPatchDto {
  private String name;
  private String topic;
  private String description;
  private OffsetDateTime startDate;
  private OffsetDateTime completionDeadline;
  @Schema(
      allowableValues =  {"24", "48"}
  )
  private Integer reviewDuration;

  public String getName() {
    return name;
  }

  public String getTopic() {
    return topic;
  }

  public String getDescription() {
    return description;
  }

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public OffsetDateTime getCompletionDeadline() {
    return completionDeadline;
  }

  public Integer getReviewDuration() {
    return reviewDuration;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate.toInstant().atOffset(ZoneOffset.UTC);
  }

  public void setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline.toInstant().atOffset(ZoneOffset.UTC);
  }

  public void setReviewDuration(Integer reviewDuration) {
    this.reviewDuration = reviewDuration;
  }
}
