package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Schema(name = "HomeworkPatch")
public class HomeworkPatchDto {
  @Pattern(regexp = "\\w+", message = "Name must contain only letters and numbers")
  private String name;
  @Pattern(regexp = "\\w+", message = "Topic must contain only letters and numbers")
  private String topic;
  @Pattern(regexp = "\\w+", message = "Description must contain only letters and numbers")
  private String description;
  @FutureOrPresent(message = "Start date must be in the future or present")
  private OffsetDateTime startDate;
  @FutureOrPresent(message = "Completion deadline must be in the future or present")
  private OffsetDateTime completionDeadline;
  @Schema(
      allowableValues =  {"24", "48"}
  )
  @Pattern(regexp = "(24)|(48)", message = "Review duration must be 24 or 48 hours")
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
