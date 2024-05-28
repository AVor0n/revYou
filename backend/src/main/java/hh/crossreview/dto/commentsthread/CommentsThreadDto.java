package hh.crossreview.dto.commentsthread;

import hh.crossreview.dto.comment.CommentDto;
import hh.crossreview.entity.enums.CommentsThreadStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
@Schema(
    name = "CommentsThread",
    requiredProperties = {
        "threadId", "authorId", "status",
        "commitSha", "filePath" , "startLine",
        "startSymbol", "endLine", "endSymbol", "comments"
    }
)
public class CommentsThreadDto {

  private Integer threadId;

  private Integer authorId;

  @Schema(allowableValues = {"ACTIVE", "RESOLVED"})
  private String status;

  private String commitSha;

  private String filePath;

  private Integer startLine;

  private Integer startSymbol;

  private Integer endLine;

  private Integer endSymbol;

  private List<CommentDto> comments;

  public CommentsThreadDto() {
  }


  public Integer getThreadId() {
    return threadId;
  }

  public CommentsThreadDto setThreadId(Integer threadId) {
    this.threadId = threadId;
    return this;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public CommentsThreadDto setAuthorId(Integer authorId) {
    this.authorId = authorId;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public CommentsThreadDto setStatus(String status) {
    this.status = status;
    return this;
  }

  public String getCommitSha() {
    return commitSha;
  }

  public CommentsThreadDto setCommitSha(String commitSha) {
    this.commitSha = commitSha;
    return this;
  }

  public String getFilePath() {
    return filePath;
  }

  public CommentsThreadDto setFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public Integer getStartLine() {
    return startLine;
  }

  public CommentsThreadDto setStartLine(Integer startLine) {
    this.startLine = startLine;
    return this;
  }

  public Integer getStartSymbol() {
    return startSymbol;
  }

  public CommentsThreadDto setStartSymbol(Integer startSymbol) {
    this.startSymbol = startSymbol;
    return this;
  }

  public Integer getEndLine() {
    return endLine;
  }

  public CommentsThreadDto setEndLine(Integer endLine) {
    this.endLine = endLine;
    return this;
  }

  public Integer getEndSymbol() {
    return endSymbol;
  }

  public CommentsThreadDto setEndSymbol(Integer endSymbol) {
    this.endSymbol = endSymbol;
    return this;
  }

  public List<CommentDto> getComments() {
    return comments;
  }

  public CommentsThreadDto setComments(List<CommentDto> comments) {
    this.comments = comments;
    return this;
  }
}
