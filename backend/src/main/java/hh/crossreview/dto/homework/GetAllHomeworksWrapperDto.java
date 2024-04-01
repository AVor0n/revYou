package hh.crossreview.dto.homework;

import java.util.List;

public class GetAllHomeworksWrapperDto {
  private List<GetHomeworkDto> data;

  public GetAllHomeworksWrapperDto(List<GetHomeworkDto> data) {
    this.data = data;
  }

  public List<GetHomeworkDto> getData() {
    return data;
  }
}
