package hh.crossreview.dto.homework;

import java.util.Date;
import java.util.List;

public class PostHomeworkDto {

  private Integer lectureId;

  private String name;

  private String topic;

  private String description;

  private List<String> departments;

  private Integer authorId;

  private String homeworkLink;

  private Date startDate;

  private Date completionDeadline;

  private Date reviewDeadline;

  public void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDepartments(List<String> departments) {
    this.departments = departments;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  public void setHomeworkLink(String homeworkLink) {
    this.homeworkLink = homeworkLink;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
  }

  public void setReviewDeadline(Date reviewDeadline) {
    this.reviewDeadline = reviewDeadline;
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

  public List<String> getDepartments() {
    return departments;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public String getHomeworkLink() {
    return homeworkLink;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getCompletionDeadline() {
    return completionDeadline;
  }

  public Date getReviewDeadline() {
    return reviewDeadline;
  }

  public Integer getLectureId() {
    return lectureId;
  }

}
