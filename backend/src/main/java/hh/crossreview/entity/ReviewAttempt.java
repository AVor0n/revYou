package hh.crossreview.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ReviewAttempt {
  @Id
  @Column(name = "review_attempt_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reviewAttemptId;

  @ManyToOne
  @JoinColumn(name = "review_id")
  private Solution review;

  @ManyToOne
  @JoinColumn(name = "solution_attempt_id")
  private Solution solutionAttempt;

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

  public Solution getReview() {
    return review;
  }

  public void setReview(Solution review) {
    this.review = review;
  }

  public Solution getSolutionAttempt() {
    return solutionAttempt;
  }

  public void setSolutionAttempt(Solution solutionAttempt) {
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
