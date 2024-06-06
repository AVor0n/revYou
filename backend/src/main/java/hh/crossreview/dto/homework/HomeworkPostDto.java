package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Schema(name = "HomeworkPost")
public class HomeworkPostDto {
  @NotBlank(message = "Field 'name' cannot be whitespaces")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @NotBlank(message = "Field 'topic' cannot be whitespaces")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String topic;
  @Pattern(regexp = ".*\\S.*", message = "Field 'description' cannot be whitespaces")
  private String description;
  @Min(value = 0L, message = "The 'lectureId' value must be positive")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer lectureId;
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
      allowableValues = {"24", "48"})
  private Integer reviewDuration;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getLectureId() {
    return lectureId;
  }

  public void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
  }

  public String getRepositoryLink() {
    return repositoryLink;
  }

  public void setRepositoryLink(String repositoryLink) {
    this.repositoryLink = repositoryLink;
  }

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate.toInstant().atOffset(ZoneOffset.UTC);
  }

  public OffsetDateTime getCompletionDeadline() {
    return completionDeadline;
  }

  public void setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline.toInstant().atOffset(ZoneOffset.UTC);
  }

  public Integer getReviewDuration() {
    return reviewDuration;
  }

  public void setReviewDuration(Integer reviewDuration) {
    this.reviewDuration = reviewDuration;
  }
}
