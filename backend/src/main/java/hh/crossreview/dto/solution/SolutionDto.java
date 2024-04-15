package hh.crossreview.dto.solution;

import hh.crossreview.dto.homework.HomeworkDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "Solution")
@SuppressWarnings({"unused"})
public class SolutionDto {

  private Integer id;

  private String branchLink;

  private String commitId;

  private Integer attemptNumber;

  private LocalDateTime creationTimestamp;

  private HomeworkDto homework;

  public Integer getId() {
    return id;
  }

  public String getBranchLink() {
    return branchLink;
  }

  public String getCommitId() {
    return commitId;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public LocalDateTime getCreationTimestamp() {
    return creationTimestamp;
  }

  public HomeworkDto getHomework() {
    return homework;
  }

  public SolutionDto setId(Integer id) {
    this.id = id;
    return this;
  }

  public SolutionDto setBranchLink(String branchLink) {
    this.branchLink = branchLink;
    return this;
  }

  public SolutionDto setCommitId(String commitId) {
    this.commitId = commitId;
    return this;
  }

  public SolutionDto setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
    return this;
  }

  public SolutionDto setCreationTimestamp(LocalDateTime creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
    return this;
  }

  public SolutionDto setHomework(HomeworkDto homework) {
    this.homework = homework;
    return this;
  }

}
