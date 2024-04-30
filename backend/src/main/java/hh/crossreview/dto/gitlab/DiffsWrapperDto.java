package hh.crossreview.dto.gitlab;

import java.util.List;

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
