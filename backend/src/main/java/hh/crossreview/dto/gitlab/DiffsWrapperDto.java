package hh.crossreview.dto.gitlab;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "DiffsWrapper", requiredProperties = {"diffs"})
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class DiffsWrapperDto {

  List<DiffDto> diffs;

  public List<DiffDto> getDiffs() {
    return diffs;
  }

  public DiffsWrapperDto setDiffs(List<DiffDto> diffs) {
    this.diffs = diffs;
    return this;
  }

}
