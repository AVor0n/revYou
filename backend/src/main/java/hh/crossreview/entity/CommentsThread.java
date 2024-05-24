package hh.crossreview.entity;

import hh.crossreview.entity.enums.CommentsThreadStatus;
import hh.crossreview.entity.interfaces.Authorable;
import hh.crossreview.entity.interfaces.Statusable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "comments_thread")
public class CommentsThread implements Statusable, Authorable {
  @Id
  @Column(name = "comments_thread_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer commentsThreadId;

  @ManyToOne
  @JoinColumn(name = "review_id")
  private Review review;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private CommentsThreadStatus status;

  @Column(name = "commit_sha")
  private String commitSha;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "start_line")
  private Integer startLine;

  @Column(name = "start_symbol")
  private Integer startSymbol;

  @Column(name = "end_line")
  private Integer endLine;

  @Column(name = "end_symbol")
  private Integer endSymbol;

  @OneToMany(
      cascade = CascadeType.REMOVE,
      mappedBy = "commentsThread",
      fetch = FetchType.EAGER)
  private List<Comment> comments;

  public CommentsThread() {
  }

  public CommentsThread(
          User author,
          Review review,
          String commitSha,
          String filePath,
          Integer startLine,
          Integer startSymbol,
          Integer endLine,
          Integer endSymbol
  ) {
    this.author = author;
    this.review = review;
    this.status = CommentsThreadStatus.ACTIVE;
    this.commitSha = commitSha;
    this.filePath = filePath;
    this.startLine = startLine;
    this.startSymbol = startSymbol;
    this.endLine = endLine;
    this.endSymbol = endSymbol;
  }

  public Integer getCommentsThreadId() {
    return commentsThreadId;
  }

  public CommentsThread setCommentsThreadId(Integer threadId) {
    this.commentsThreadId = threadId;
    return this;
  }


  @Override
  public Integer getAuthorId() {
    return author.getUserId();
  }

  public CommentsThread setAuthor(User author) {
    this.author = author;
    return this;
  }

  public CommentsThreadStatus getStatus() {
    return status;
  }

  public CommentsThread setStatus(CommentsThreadStatus status) {
    this.status = status;
    return this;
  }

  public String getCommitSha() {
    return commitSha;
  }

  public CommentsThread setCommitSha(String commitSha) {
    this.commitSha = commitSha;
    return this;
  }

  public String getFilePath() {
    return filePath;
  }

  public CommentsThread setFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public Integer getStartLine() {
    return startLine;
  }

  public CommentsThread setStartLine(Integer startLine) {
    this.startLine = startLine;
    return this;
  }

  public Integer getStartSymbol() {
    return startSymbol;
  }

  public CommentsThread setStartSymbol(Integer startSymbol) {
    this.startSymbol = startSymbol;
    return this;
  }

  public Integer getEndLine() {
    return endLine;
  }

  public CommentsThread setEndLine(Integer endLine) {
    this.endLine = endLine;
    return this;
  }

  public Integer getEndSymbol() {
    return endSymbol;
  }

  public CommentsThread setEndSymbol(Integer endSymbol) {
    this.endSymbol = endSymbol;
    return this;
  }

  public Review getReview() {
    return review;
  }

  public CommentsThread setReview(Review review) {
    this.review = review;
    return this;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public CommentsThread setComments(List<Comment> comments) {
    this.comments = comments;
    return this;
  }
}
