package hh.crossreview.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Schema(
    name = "Comment",
    requiredProperties = {
        "commentId", "authorId", "content", "createdAt", "updatedAt"
    }
)
public class CommentDto {
  private Integer commentId;

  private Integer authorId;

  private String content;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

  public CommentDto() {
  }

  public Integer getCommentId() {
    return commentId;
  }

  public CommentDto setCommentId(Integer commentId) {
    this.commentId = commentId;
    return this;
  }

  public String getContent() {
    return content;
  }

  public CommentDto setContent(String content) {
    this.content = content;
    return this;
  }


  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public CommentDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt.atOffset(ZoneOffset.UTC);
    return this;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public CommentDto setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt != null ? updatedAt.atOffset(ZoneOffset.UTC) : null;
    return this;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public CommentDto setAuthorId(Integer authorId) {
    this.authorId = authorId;
    return this;
  }
}
