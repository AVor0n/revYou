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
@Table(name = "solution_attempt")
@SuppressWarnings({"unused"})
public class SolutionAttempt {

  @Id
  @Column(name = "solution_attempt_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer solutionAttemptId;

  @Column(name = "commit_id")
  private String commitId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @ManyToOne
  @JoinColumn(name = "solution_id")
  private Solution solution;

  public SolutionAttempt(String commitId, Solution solution) {
    this.commitId = commitId;
    this.solution = solution;
    this.createdAt = LocalDateTime.now();
  }

  public SolutionAttempt() {}

  public Integer getSolutionAttemptId() {
    return solutionAttemptId;
  }

  public String getCommitId() {
    return commitId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Solution getSolution() {
    return solution;
  }

}
