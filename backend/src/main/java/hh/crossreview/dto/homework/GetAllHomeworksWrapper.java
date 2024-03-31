package hh.crossreview.dto.homework;

import java.util.List;

public class GetAllHomeworksWrapper {
  private List<GetHomeworkDto> data;

  public GetAllHomeworksWrapper(List<GetHomeworkDto> data) {
    this.data = data;
  }

  public List<GetHomeworkDto> getData() {
    return data;
  }
}
