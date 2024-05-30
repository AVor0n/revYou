package hh.crossreview.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "LectureWrapper", requiredProperties = {"data"})
@SuppressWarnings({"unused"})
public class LectureWrapperDto {

  private List<LectureDto> data;

  public LectureWrapperDto(List<LectureDto> data) {
    this.data = data;
  }

  public List<LectureDto> getData() {
    return data;
  }

  public void setData(List<LectureDto> data) {
    this.data = data;
  }
}
