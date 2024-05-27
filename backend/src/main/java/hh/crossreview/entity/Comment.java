package hh.crossreview.entity;

import hh.crossreview.entity.interfaces.Authorable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment implements Authorable {

  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer commentId;

  @ManyToOne
  @JoinColumn(name = "comments_thread_id")
  private CommentsThread commentsThread;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @Column(name = "content")
  private String content;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Comment() {
  }

  public Comment(
          CommentsThread commentsThread,
          User author,
          String content
  ) {
    this.commentsThread = commentsThread;
    this.author = author;
    this.content = content;
    this.createdAt = LocalDateTime.now();
  }


  public Integer getCommentId() {
    return commentId;
  }

  public Comment setCommentId(Integer commentId) {
    this.commentId = commentId;
    return this;
  }

  public CommentsThread getCommentsThread() {
    return commentsThread;
  }

  public Comment setCommentsThread(CommentsThread commentsThread) {
    this.commentsThread = commentsThread;
    return this;
  }

  @Override
  public Integer getAuthorId() {
    return author.getUserId();
  }


  public Comment setAuthor(User author) {
    this.author = author;
    return this;
  }

  public String getContent() {
    return content;
  }

  public Comment setContent(String content) {
    this.content = content;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Comment setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public Comment setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }
}
