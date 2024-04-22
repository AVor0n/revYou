package hh.crossreview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "сode_review_feedback")
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  private Integer feedbackId;

  @ManyToOne
  @JoinColumn(name = "reviewer_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_reviewer_id"))
  private User reviewer;

  @ManyToOne
  @JoinColumn(name = "student_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_student_id"))
  private User student;

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "feedback_date")
  private LocalDateTime feedbackDate;

  public User getReviewer() {
    return reviewer;
  }

  public Feedback setReviewer(User reviewer) {
    this.reviewer = reviewer;
    return this;
  }

  public User getStudent() {
    return student;
  }

  public Feedback setStudent(User student) {
    this.student = student;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Feedback setDescription(String description) {
    this.description = description;
    return this;
  }

  public Integer getRating() {
    return rating;
  }

  public Feedback setRating(Integer rating) {
    this.rating = rating;
    return this;
  }

  public LocalDateTime getFeedbackDate() {
    return feedbackDate;
  }

  public Feedback setFeedbackDate(LocalDateTime feedbackDate) {
    this.feedbackDate = feedbackDate;
    return this;
  }

  public Integer getFeedbackId() {
    return feedbackId;
  }

  public void setFeedbackId(Integer feedbackId) {
    this.feedbackId = feedbackId;
  }
}
