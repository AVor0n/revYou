package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Schema(name = "HomeworkPost")
public class HomeworkPostDto {
  @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Name can only contain letters, numbers")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Name can only contain letters, numbers")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String topic;
  @NotBlank
  private String description;
  @Pattern(regexp = "[0-9]+", message = "Field 'lectureId' must be a number")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer lectureId;
  @Pattern(regexp = "https://.*", message = "Field 'repositoryLink' must start with 'https://'")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String repositoryLink;
  @FutureOrPresent(message = "Start date must be in the future or present")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private OffsetDateTime startDate;
  @FutureOrPresent(message = "Completion deadline must be in the future or present")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private OffsetDateTime completionDeadline;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      allowableValues =  {"24", "48"})
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

  public Integer getLectureId() {
    return lectureId;
  }

  public String getRepositoryLink() {
    return repositoryLink;
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

  public void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
  }

  public void setRepositoryLink(String repositoryLink) {
    this.repositoryLink = repositoryLink;
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
