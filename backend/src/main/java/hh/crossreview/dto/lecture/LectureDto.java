package hh.crossreview.dto.lecture;

import hh.crossreview.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Schema(name = "Lecture", requiredProperties = {"lectureId",
    "name",
    "lectureDate",
    "zoomLink",
    "presentationLink",
    "lector"})
public class LectureDto implements Serializable {
  @NotNull
  private Integer lectureId;
  @NotNull
  private String name;
  private LocalDateTime lectureDate;
  private String zoomLink;
  private String presentationLink;
  @NotNull(message = "Field 'teacherId' couldn't be empty")
  private UserDto lector;

  public Integer getLectureId() {
    return lectureId;
  }

  public LectureDto setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
    return this;
  }

  public String getName() {
    return name;
  }

  public LectureDto setName(String name) {
    this.name = name;
    return this;
  }

  public LocalDateTime getLectureDate() {
    return lectureDate;
  }

  public LectureDto setLectureDate(LocalDateTime lectureDate) {
    this.lectureDate = lectureDate;
    return this;
  }

  public String getZoomLink() {
    return zoomLink;
  }

  public LectureDto setZoomLink(String zoomLink) {
    this.zoomLink = zoomLink;
    return this;
  }

  public String getPresentationLink() {
    return presentationLink;
  }

  public LectureDto setPresentationLink(String presentationLink) {
    this.presentationLink = presentationLink;
    return this;
  }

  public UserDto getLector() {
    return lector;
  }

  public LectureDto setLector(UserDto lector) {
    this.lector = lector;
    return this;
  }
}
