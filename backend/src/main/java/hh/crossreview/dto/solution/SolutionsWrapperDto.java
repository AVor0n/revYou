package hh.crossreview.dto.solution;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "SolutionsWrapper")
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class SolutionsWrapperDto {
  private List<SolutionDto> data;

  public SolutionsWrapperDto(List<SolutionDto> data) {
    this.data = data;
  }

  public List<SolutionDto> getData() {
    return data;
  }
}
