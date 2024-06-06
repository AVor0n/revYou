package hh.crossreview.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Schema(name = "LecturePatch")
public class LecturePatchDto {
  @Pattern(regexp = ".*\\S.*", message = "Field 'name' cannot be empty string")
  private String name;
  @FutureOrPresent(message = "Field 'lectureDate' must be in the future or present")
  private OffsetDateTime lectureDate;
  @Pattern(regexp = "https://.*", message = "Field 'zoomLink' must start with 'https://'")
  private String zoomLink;
  @Pattern(regexp = "https://.*", message = "Field 'presentationLink' must start with 'https://'")
  private String presentationLink;
  @Min(value = 0L, message = "The 'lectorId' value must be positive")
  private Integer lectorId;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLectorId() {
    return lectorId;
  }

  public void setLectorId(Integer lectorId) {
    this.lectorId = lectorId;
  }
}
