package hh.crossreview.entity;

import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.interfaces.Authorable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table
@SuppressWarnings({"unused"})
public class Solution implements Authorable {

  @Id
  @Column(name = "solution_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer solutionId;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private SolutionStatus status;

  @Column(name = "branch_link")
  private String branchLink;

  @Column(name = "approve_score")
  private Integer approveScore;

  @Column(name = "review_score")
  private Integer reviewScore;

  @ManyToOne
  @JoinColumn(name = "homework_id")
  private Homework homework;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private User student;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "solution")
  private List<SolutionAttempt> solutionAttempts;

  @Override
  public Integer getAuthorId() {
    return student.getUserId();
  }

  public Integer getSolutionId() {
    return solutionId;
  }

  public SolutionStatus getStatus() {
    return status;
  }

  public String getBranchLink() {
    return branchLink;
  }

  public Integer getApproveScore() {
    return approveScore;
  }

  public Integer getReviewScore() {
    return reviewScore;
  }

  public Homework getHomework() {
    return homework;
  }

  public User getStudent() {
    return student;
  }

  public List<SolutionAttempt> getSolutionAttempts() {
    return solutionAttempts;
  }

  public Solution setSolutionId(Integer solutionId) {
    this.solutionId = solutionId;
    return this;
  }

  public Solution setStatus(SolutionStatus status) {
    this.status = status;
    return this;
  }

  public Solution setBranchLink(String branchLink) {
    this.branchLink = branchLink;
    return this;
  }

  public Solution setApproveScore(Integer approveScore) {
    this.approveScore = approveScore;
    return this;
  }

  public Solution setReviewScore(Integer reviewScore) {
    this.reviewScore = reviewScore;
    return this;
  }

  public Solution setHomework(Homework homework) {
    this.homework = homework;
    return this;
  }

  public Solution setStudent(User student) {
    this.student = student;
    return this;
  }

  public Solution setSolutionAttempts(List<SolutionAttempt> solutionAttempts) {
    this.solutionAttempts = solutionAttempts;
    return this;
  }
}
