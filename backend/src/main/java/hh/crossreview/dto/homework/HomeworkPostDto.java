package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(name = "HomeworkPost")
public class HomeworkPostDto {

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String topic;

  private String description;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer lectureId;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String repositoryLink;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Date startDate;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Date completionDeadline;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
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

  public Integer getLectureId() {
    return lectureId;
  }

  public String getRepositoryLink() {
    return repositoryLink;
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

  public void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
  }

  public void setRepositoryLink(String repositoryLink) {
    this.repositoryLink = repositoryLink;
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
