package hh.crossreview.dto.homework;

import java.util.List;

public class AllHomeworksWrapperDto {
  private List<HomeworkDto> data;

  public AllHomeworksWrapperDto(List<HomeworkDto> data) {
    this.data = data;
  }

  public List<HomeworkDto> getData() {
    return data;
  }
}
