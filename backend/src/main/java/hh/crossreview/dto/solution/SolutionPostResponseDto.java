package hh.crossreview.dto.solution;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SolutionPostResponse")
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class SolutionPostResponseDto {
  private Integer solutionId;
  private Integer attemptNumber;

  public SolutionPostResponseDto(Integer solutionId, Integer attemptNumber) {
    this.solutionId = solutionId;
    this.attemptNumber = attemptNumber;
  }

  public Integer getSolutionId() {
    return solutionId;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

}
