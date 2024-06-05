package hh.crossreview.dto.commentsthread;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "CommentsThreadResolveDto",
    requiredProperties = {"status"}
)
public class CommentsThreadResolveDto {
  @Schema(allowableValues = {"ACTIVE", "RESOLVED"})
  private String status;

  public String getStatus() {
    return status;
  }

  public CommentsThreadResolveDto setStatus(String status) {
    this.status = status;
    return this;
  }
}
