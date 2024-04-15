package hh.crossreview.entity;

import hh.crossreview.entity.enums.HomeworkRecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "homework_record")
@SuppressWarnings({"unused"})
public class HomeworkRecord {

  @Id
  @Column(name = "homework_record_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer homeworkRecordId;

  @Column
  @Enumerated(EnumType.STRING)
  private HomeworkRecordStatus status;

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

  public HomeworkRecordStatus getStatus() {
    return status;
  }

  public HomeworkRecord setHomeworkRecordId(Integer homeworkRecordId) {
    this.homeworkRecordId = homeworkRecordId;
    return this;
  }

  public HomeworkRecord setStatus(HomeworkRecordStatus status) {
    this.status = status;
    return this;
  }

  public HomeworkRecord setApproveScore(Integer approveScore) {
    this.approveScore = approveScore;
    return this;
  }

  public HomeworkRecord setReviewScore(Integer reviewScore) {
    this.reviewScore = reviewScore;
    return this;
  }

  public HomeworkRecord setHomework(Homework homework) {
    this.homework = homework;
    return this;
  }

  public HomeworkRecord setStudent(User student) {
    this.student = student;
    return this;
  }
}
