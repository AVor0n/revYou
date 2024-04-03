package hh.crossreview.dto.homework;

import java.util.List;

public class HomeworksWrapperDto {
  private List<HomeworkDto> data;

  public HomeworksWrapperDto(List<HomeworkDto> data) {
    this.data = data;
  }

  public List<HomeworkDto> getData() {
    return data;
  }
}
