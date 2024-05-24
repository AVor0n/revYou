package hh.crossreview.dto.commentsthread;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "ThreadWrapper", requiredProperties = {"data"})
public class CommentsThreadWrapperDto {
  private List<CommentsThreadDto> data;

  public CommentsThreadWrapperDto(List<CommentsThreadDto> data) {
    this.data = data;
  }

  public List<CommentsThreadDto> getData() {
    return data;
  }

  public CommentsThreadWrapperDto setData(List<CommentsThreadDto> data) {
    this.data = data;
    return this;
  }
}
