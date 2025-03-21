package hh.crossreview.entity;

import hh.crossreview.entity.enums.ReviewDuration;
import hh.crossreview.entity.enums.converters.ReviewDurationAttributeConverter;
import hh.crossreview.entity.interfaces.Authorable;
import hh.crossreview.entity.interfaces.Cohortable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Homework implements Authorable, Cohortable {

  @Id
  @Column(name = "homework_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer homeworkId;

  @NotBlank(message = "Homework 'repositoryLink' couldn't be empty")
  @Column(name = "repository_link")
  private String repositoryLink;

  @NotBlank(message = "Homework 'name' couldn't be empty")
  @Column
  private String name;

  @NotBlank(message = "Homework 'topic' couldn't be empty")
  @Column
  private String topic;

  @Column
  private String description;

  @Column(name = "source_commit_id")
  private String sourceCommitId;

  @NotNull(message = "Homework 'startDate' couldn't be empty")
  @Column(name = "start_date")
  private Date startDate;

  @NotNull(message = "Homework 'completionDeadline' couldn't be empty")
  @Column(name = "completion_deadline")
  private Date completionDeadline;

  @NotNull(message = "Something wrong with 'reviewDuration': valid numbers is 24 and 48")
  @Column(name = "review_duration")
  @Convert(converter = ReviewDurationAttributeConverter.class)
  private ReviewDuration reviewDuration;

  @NotNull(message = "Homework 'lectureId' couldn't be empty")
  @ManyToOne
  @JoinColumn(name = "lecture_id")
  private Lecture lecture;

  @NotNull(message = "Homework must have an author")
  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  public Integer getHomeworkId() {
    return homeworkId;
  }

  public String getRepositoryLink() {
    return repositoryLink;
  }

  public String getName() {
    return name;
  }

  public String getTopic() {
    return topic;
  }

  public String getDescription() {
    return description;
  }

  public String getSourceCommitId() {
    return sourceCommitId;
  }

  public Date getStartDate() {
    return startDate;
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

  public User getAuthor() {
    return author;
  }

  public Homework setHomeworkId(Integer homeworkId) {
    this.homeworkId = homeworkId;
    return this;
  }

  public Homework setRepositoryLink(String homeworkLink) {
    this.repositoryLink = homeworkLink;
    return this;
  }

  public Homework setName(String title) {
    this.name = title;
    return this;
  }

  public Homework setTopic(String theme) {
    this.topic = theme;
    return this;
  }

  public Homework setDescription(String description) {
    this.description = description;
    return this;
  }

  public Homework setSourceCommitId(String sourceCommitId) {
    this.sourceCommitId = sourceCommitId;
    return this;
  }

  public Homework setStartDate(Date creationTimestamp) {
    this.startDate = creationTimestamp;
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

  public Homework setAuthor(User author) {
    this.author = author;
    return this;
  }

  @Override
  public Integer getAuthorId() {
    return author.getUserId();
  }

  @Override
  public List<Cohort> getCohorts() {
    return lecture.getCohorts();
  }

}
