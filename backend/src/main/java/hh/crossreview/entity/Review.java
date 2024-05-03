package hh.crossreview.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "review")
public class Review {

  @Id
  @Column(name = "review_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reviewId;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private User student;

  @ManyToOne
  @JoinColumn(name = "reviewer_id")
  private User reviewer;

  @Column(name = "status")
  private String status;

  @ManyToOne
  @JoinColumn(name = "solution_id")
  private Solution solution;

  @OneToMany(
          cascade = CascadeType.ALL,
          mappedBy = "review",
          fetch = FetchType.EAGER)
  private List<ReviewAttempt> reviewAttempts;


  public Integer getReviewId() {
    return reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public User getStudent() {
    return student;
  }

  public void setStudent(User student) {
    this.student = student;
  }

  public User getReviewer() {
    return reviewer;
  }

  public void setReviewer(User reviewer) {
    this.reviewer = reviewer;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Solution getSolution() {
    return solution;
  }

  public void setSolution(Solution solution) {
    this.solution = solution;
  }

  public List<ReviewAttempt> getReviewAttempts() {
    return reviewAttempts;
  }

  public void setReviewAttempts(List<ReviewAttempt> reviewAttempts) {
    this.reviewAttempts = reviewAttempts;
  }
}
