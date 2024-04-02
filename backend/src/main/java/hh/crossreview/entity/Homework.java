package hh.crossreview.entity;

import hh.crossreview.entity.enums.ReviewDuration;
import hh.crossreview.entity.enums.converters.ReviewDurationAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Homework {

  @Id
  @Column(name = "homework_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer homeworkId;

  @Column(name = "homework_link")
  private String homeworkLink;

  @Column
  private String title;

  @Column
  private String theme;

  @Column
  private String description;

  @Column(name = "start_timestamp")
  private Date startTimestamp;

  @Column(name = "completion_deadline")
  private Date completionDeadline;

  @Column(name = "review_duration")
  @Convert(converter = ReviewDurationAttributeConverter.class)
  private ReviewDuration reviewDuration;

  @ManyToOne
  @JoinColumn(name = "lecture_id")
  private Lecture lecture;

  public Integer getHomeworkId() {
    return homeworkId;
  }

  public String getHomeworkLink() {
    return homeworkLink;
  }

  public String getTitle() {
    return title;
  }

  public String getTheme() {
    return theme;
  }

  public String getDescription() {
    return description;
  }

  public Date getStartTimestamp() {
    return startTimestamp;
  }

  public Date getCompletionDeadline() {
    return completionDeadline;
  }

  public ReviewDuration getReviewDuration() {
    return reviewDuration;
  }

  public Lecture getLecture() {
    return lecture;
  }

  public Homework setHomeworkLink(String homeworkLink) {
    this.homeworkLink = homeworkLink;
    return this;
  }

  public Homework setTitle(String title) {
    this.title = title;
    return this;
  }

  public Homework setTheme(String theme) {
    this.theme = theme;
    return this;
  }

  public Homework setDescription(String description) {
    this.description = description;
    return this;
  }

  public Homework setStartTimestamp(Date creationTimestamp) {
    this.startTimestamp = creationTimestamp;
    return this;
  }

  public Homework setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
    return this;
  }

  public Homework setReviewDuration(ReviewDuration reviewDuration) {
    this.reviewDuration = reviewDuration;
    return this;
  }

  public Homework setLecture(Lecture lecture) {
    this.lecture = lecture;
    return this;
  }

}
