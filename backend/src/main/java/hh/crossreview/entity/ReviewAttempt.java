package hh.crossreview.entity;

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
@Table(name = "review_attempt")
public class ReviewAttempt {
  @Id
  @Column(name = "review_attempt_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reviewAttemptId;

  @ManyToOne
  @JoinColumn(name = "review_id")
  private Review review;

  @ManyToOne
  @JoinColumn(name = "solution_attempt_id")
  private SolutionAttempt solutionAttempt;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "finished_at")
  private LocalDateTime finishedAt;

  @Column(name = "resolution")
  private String resolution;

  public Integer getReviewAttemptId() {
    return reviewAttemptId;
  }

  public void setReviewAttemptId(Integer reviewAttemptId) {
    this.reviewAttemptId = reviewAttemptId;
  }

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public SolutionAttempt getSolutionAttempt() {
    return solutionAttempt;
  }

  public void setSolutionAttempt(SolutionAttempt solutionAttempt) {
    this.solutionAttempt = solutionAttempt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(LocalDateTime finishedAt) {
    this.finishedAt = finishedAt;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }
}
