package hh.crossreview.dto.comment;

import hh.crossreview.entity.CommentsThread;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "Comment")
public class CommentDto {
  private Integer commentId;

  private Integer authorId;

  private CommentsThread commentsThread;

  private String content;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public CommentDto() {
  }

  public Integer getCommentId() {
    return commentId;
  }

  public CommentDto setCommentId(Integer commentId) {
    this.commentId = commentId;
    return this;
  }

  public CommentsThread getThread() {
    return commentsThread;
  }

  public CommentDto setThread(CommentsThread commentsThread) {
    this.commentsThread = commentsThread;
    return this;
  }

  public String getContent() {
    return content;
  }

  public CommentDto setContent(String content) {
    this.content = content;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public CommentDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public CommentDto setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
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
