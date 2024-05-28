package hh.crossreview.dto.commentsthread;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ThreadPost",
    requiredProperties = {
        "reviewId", "commitSha", "filePath" , "startLine",
        "startSymbol", "endLine", "endSymbol", "content"
    }
)
public class CommentsThreadPostDto {

  private Integer reviewId;

  private String commitSha;

  private String filePath;

  private Integer startLine;

  private Integer startSymbol;

  private Integer endLine;

  private Integer endSymbol;

  private String content;

  public String getCommitSha() {
    return commitSha;
  }

  public CommentsThreadPostDto setCommitSha(String commitSha) {
    this.commitSha = commitSha;
    return this;
  }

  public String getFilePath() {
    return filePath;
  }

  public CommentsThreadPostDto setFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public Integer getStartLine() {
    return startLine;
  }

  public CommentsThreadPostDto setStartLine(Integer startLine) {
    this.startLine = startLine;
    return this;
  }

  public Integer getStartSymbol() {
    return startSymbol;
  }

  public CommentsThreadPostDto setStartSymbol(Integer startSymbol) {
    this.startSymbol = startSymbol;
    return this;
  }

  public Integer getEndLine() {
    return endLine;
  }

  public CommentsThreadPostDto setEndLine(Integer endLine) {
    this.endLine = endLine;
    return this;
  }

  public Integer getEndSymbol() {
    return endSymbol;
  }

  public CommentsThreadPostDto setEndSymbol(Integer endSymbol) {
    this.endSymbol = endSymbol;
    return this;
  }

  public String getContent() {
    return content;
  }

  public CommentsThreadPostDto setContent(String content) {
    this.content = content;
    return this;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public CommentsThreadPostDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
    return this;
  }
}
