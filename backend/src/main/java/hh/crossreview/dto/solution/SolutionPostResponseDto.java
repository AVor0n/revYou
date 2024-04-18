package hh.crossreview.dto.solution;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SolutionPostResponse")
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class SolutionPostResponseDto {
  private Integer solutionId;

  public SolutionPostResponseDto(Integer solutionId) {
    this.solutionId = solutionId;
  }

  public Integer getSolutionId() {
    return solutionId;
  }

}
