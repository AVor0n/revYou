package hh.crossreview.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "ReviewWrapper", requiredProperties = {"data"})
public class ReviewWrapperDto {
  private List<ReviewDto> data;

  public ReviewWrapperDto(List<ReviewDto> data) {
    this.data = data;
  }

  public List<ReviewDto> getData() {
    return data;
  }
}
