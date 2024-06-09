package hh.crossreview.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Schema(name = "LecturePost")
public class LecturePostDto {
  @NotBlank(message = "Field 'name' cannot be empty and whitespaces")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @FutureOrPresent(message = "Lecture date must be in the future or present")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private OffsetDateTime lectureDate;
  private String zoomLink;
  private String presentationLink;
  @Min(value = 0L, message = "The 'lectorId' value must be positive")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer lectorId;
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
