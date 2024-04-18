package hh.crossreview.dto.solution;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "SolutionPost")
public class SolutionPostDto {

  @NotBlank(message = "Solution 'branchLink' couldn't be empty")
  @Pattern(regexp = ".+/[^/]+/[^/]+/-/tree/[\\w\\-./]+.*")
  private String branchLink;
  public void setBranchLink(String branchLink) {
    this.branchLink = branchLink;
  }

  public String getBranchLink() {
    return branchLink;
  }
}
