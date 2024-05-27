package hh.crossreview.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Date;

@Schema(name = "LecturePatch")
public class LecturePatchDto {
  private String name;
  private LocalDateTime lectureDate;
  private String zoomLink;
  private String presentationLink;
  private Integer lectorId;

  public LocalDateTime getLectureDate() {
    return lectureDate;
  }

  public void setLectureDate(LocalDateTime lectureDate) {
    this.lectureDate = lectureDate;
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
