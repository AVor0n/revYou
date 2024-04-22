package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "HomeworksWrapper")
public class HomeworksWrapperDto {
  private List<HomeworkDto> data;

  public HomeworksWrapperDto(List<HomeworkDto> data) {
    this.data = data;
  }

  public HomeworksWrapperDto() {}

  public List<HomeworkDto> getData() {
    return data;
  }
}
