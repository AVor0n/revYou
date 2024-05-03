package hh.crossreview.entity;

import hh.crossreview.entity.enums.SolutionStatus;
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
@Table
@SuppressWarnings({"unused"})
public class Solution implements Authorable, Statusable {

  @Id
  @Column(name = "solution_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer solutionId;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private SolutionStatus status;

  @Column(name = "project_id")
  private Integer projectId;

  @Column
  private String branch;

  @Column(name = "source_commit_id")
  private String sourceCommitId;

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
      mappedBy = "solution",
      fetch = FetchType.EAGER)
  private List<SolutionAttempt> solutionAttempts;

  @Override
  public Integer getAuthorId() {
    return student.getUserId();
  }

  public Integer getSolutionId() {
    return solutionId;
  }

  @Override
  public SolutionStatus getStatus() {
    return status;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public String getBranch() {
    return branch;
  }

  public String getSourceCommitId() {
    return sourceCommitId;
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

  public Solution setProjectId(Integer projectId) {
    this.projectId = projectId;
    return this;
  }

  public Solution setBranch(String branch) {
    this.branch = branch;
    return this;
  }

  public Solution setSourceCommitId(String sourceCommitId) {
    this.sourceCommitId = sourceCommitId;
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
