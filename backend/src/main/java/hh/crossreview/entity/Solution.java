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
@Table
@SuppressWarnings({"unused"})
public class Solution implements Authorable {

  @Id
  @Column(name = "solution_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer solutionId;

  @Column(name = "branch_link")
  private String branchLink;

  @Column(name = "commit_id")
  private String commitId;

  @Column(name = "attempt_number")
  private Integer attemptNumber;

  @Column(name = "creation_timestamp")
  private LocalDateTime creationTimestamp;

  @ManyToOne
  @JoinColumn(name = "homework_id")
  private Homework homework;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @Override
  public Integer getAuthorId() {
    return author.getUserId();
  }

  public Integer getSolutionId() {
    return solutionId;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public String getBranchLink() {
    return branchLink;
  }

  public String getCommitId() {
    return commitId;
  }

  public LocalDateTime getCreationTimestamp() {
    return creationTimestamp;
  }

  public Homework getHomework() {
    return homework;
  }

  public User getAuthor() {
    return author;
  }

  public Solution setSolutionId(Integer solutionId) {
    this.solutionId = solutionId;
    return this;
  }

  public Solution setBranchLink(String branchLink) {
    this.branchLink = branchLink;
    return this;
  }

  public Solution setCommitId(String commitId) {
    this.commitId = commitId;
    return this;
  }

  public Solution setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
    return this;
  }

  public Solution setCreationTimestamp(LocalDateTime creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
    return this;
  }

  public Solution setHomework(Homework homework) {
    this.homework = homework;
    return this;
  }

  public Solution setAuthor(User author) {
    this.author = author;
    return this;
  }

}
