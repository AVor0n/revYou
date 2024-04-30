package hh.crossreview.dto.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Diff", requiredProperties = {
    "new_path", "old_path", "new_file",
    "renamed_file", "deleted_file", "generated_file"}
)
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class DiffDto {

  @JsonProperty("new_path")
  private String newPath;
  @JsonProperty("old_path")
  private String oldPath;
  @JsonProperty("new_file")
  private Boolean newFile;
  @JsonProperty("renamed_file")
  private Boolean renamedFile;
  @JsonProperty("deleted_file")
  private Boolean deletedFile;
  @JsonProperty("generated_file")
  private Boolean generatedFile;

  public String getNewPath() {
    return newPath;
  }

  public String getOldPath() {
    return oldPath;
  }

  public Boolean getNewFile() {
    return newFile;
  }

  public Boolean getRenamedFile() {
    return renamedFile;
  }

  public Boolean getDeletedFile() {
    return deletedFile;
  }

  public Boolean getGeneratedFile() {
    return generatedFile;
  }

  public DiffDto setNewPath(String newPath) {
    this.newPath = newPath;
    return this;
  }

  public DiffDto setOldPath(String oldPath) {
    this.oldPath = oldPath;
    return this;
  }

  public DiffDto setNewFile(Boolean newFile) {
    this.newFile = newFile;
    return this;
  }

  public DiffDto setRenamedFile(Boolean renamedFile) {
    this.renamedFile = renamedFile;
    return this;
  }

  public DiffDto setDeletedFile(Boolean deletedFile) {
    this.deletedFile = deletedFile;
    return this;
  }

  public DiffDto setGeneratedFile(Boolean generatedFile) {
    this.generatedFile = generatedFile;
    return this;
  }

}
