package hh.crossreview.external.gitlab.entity;

@SuppressWarnings({"FieldMayBeFinal"})
public class RepositoryInfo {
  private String projectPath;
  private Integer projectId;
  private String branch;

  public RepositoryInfo(String projectPath, String branch) {
    this.projectPath = projectPath;
    this.branch = branch;
  }

  public RepositoryInfo(Integer projectId, String branch) {
    this.projectId = projectId;
    this.branch = branch;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public String getProjectPath() {
    return projectPath;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public String getBranch() {
    return branch;
  }

}
