package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(name = "HomeworkPatch")
public class HomeworkPatchDto {
  private String name;
  private String topic;
  private String description;
  private Date startDate;
  private Date completionDeadline;
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

  public Date getStartDate() {
    return startDate;
  }

  public Date getCompletionDeadline() {
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
    this.startDate = startDate;
  }

  public void setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
  }

  public void setReviewDuration(Integer reviewDuration) {
    this.reviewDuration = reviewDuration;
  }
}
