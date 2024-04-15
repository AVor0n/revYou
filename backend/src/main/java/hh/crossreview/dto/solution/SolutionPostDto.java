package hh.crossreview.dto.solution;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "SolutionPost")
public class SolutionPostDto {

  @NotBlank(message = "Solution 'branchLink' couldn't be empty")
  @Pattern(regexp = ".+/[^/]+/[^/]+/-/tree/[\\w\\-./]+.*")
  private String branchLink;
  @JsonIgnore
  private String commitId;
  @JsonIgnore
  private Integer attemptNumber;

  public void setBranchLink(String branchLink) {
    this.branchLink = branchLink;
  }

  public String getBranchLink() {
    return branchLink;
  }

  public String getCommitId() {
    return commitId;
  }

  public void setCommitId(String commitId) {
    this.commitId = commitId;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
  }
}
