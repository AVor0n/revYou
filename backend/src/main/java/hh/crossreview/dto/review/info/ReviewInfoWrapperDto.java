package hh.crossreview.dto.review.info;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "ReviewInfoWrapper", requiredProperties = {"data", "sourceCommitId"})
@SuppressWarnings({"unused"})
public class ReviewInfoWrapperDto {

  private List<ReviewInfoDto> data;
  private String sourceCommitId;

  public ReviewInfoWrapperDto(List<ReviewInfoDto> reviewsInfo, String sourceCommitId) {
    this.data = reviewsInfo;
    this.sourceCommitId = sourceCommitId;
  }

  public List<ReviewInfoDto> getData() {
    return data;
  }

  public String getSourceCommitId() {
    return sourceCommitId;
  }

  public ReviewInfoWrapperDto setData(List<ReviewInfoDto> data) {
    this.data = data;
    return this;
  }

  public ReviewInfoWrapperDto setSourceCommitId(String sourceCommitId) {
    this.sourceCommitId = sourceCommitId;
    return this;
  }
}
