package hh.crossreview.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Schema(name = "LecturePost")
public class LecturePostDto {
  @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Name can only contain letters, numbers and underscores")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @FutureOrPresent(message = "Lecture date must be in the future or present")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private OffsetDateTime lectureDate;
  @Pattern(
      regexp = "https://[a-zA-Z0-9]+.zoom.us/j/[a-zA-Z0-9]+",
      message = "Zoom link must be a valid Zoom link"
  )
  private String zoomLink;
  @Pattern(regexp = "https://.*", message = "Field 'presentationLink' must start with 'https://'")
  private String presentationLink;
  @Pattern(regexp = "[0-9]+", message = "Field 'lectorId' must be a number")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer lectorId;
  @Pattern(regexp = "[0-9]+", message = "Field 'cohortId' must be a number")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private List<Integer> cohortsId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OffsetDateTime getLectureDate() {
    return lectureDate;
  }

  public void setLectureDate(LocalDateTime lectureDate) {
    this.lectureDate = lectureDate.atOffset(ZoneOffset.UTC);
  }

  public String getZoomLink() {
    return zoomLink;
  }

  public void setZoomLink(String zoomLink) {
    this.zoomLink = zoomLink;
  }

  public String getPresentationLink() {
    return presentationLink;
  }

  public void setPresentationLink(String presentationLink) {
    this.presentationLink = presentationLink;
  }

  public Integer getLectorId() {
    return lectorId;
  }

  public void setLectorId(Integer lectorId) {
    this.lectorId = lectorId;
  }

  public List<Integer> getCohortsId() {
    return cohortsId;
  }

  public void setCohortsId(List<Integer> cohortsId) {
    this.cohortsId = cohortsId;
  }
}
