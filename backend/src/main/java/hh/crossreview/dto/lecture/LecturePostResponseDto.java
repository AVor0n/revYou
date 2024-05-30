package hh.crossreview.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LecturePostResponse")
@SuppressWarnings({"FieldMayBeFinal"})
public class LecturePostResponseDto {
  private Integer lectureId;

  public LecturePostResponseDto(Integer lectureId) {
    this.lectureId = lectureId;
  }

  public Integer getLectureId() {
    return lectureId;
  }
}
