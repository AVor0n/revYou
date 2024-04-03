package hh.crossreview.dto.homework;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class HomeworkDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer id;

  private String name;

  private String topic;

  private String description;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<String> departments;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private HomeworkAuthorDto author;
  private HomeworkLectureDto lecture;

  private String repositoryLink;

  private Date startDate;

  private Date completionDeadline;

  private Integer reviewDuration;

  public HomeworkDto setId(Integer id) {
    this.id = id;
    return this;
  }

  public HomeworkDto setName(String name) {
    this.name = name;
    return this;
  }

  public HomeworkDto setTopic(String topic) {
    this.topic = topic;
    return this;
  }

  public HomeworkDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public HomeworkDto setDepartments(List<String> departments) {
    this.departments = departments;
    return this;
  }

  public HomeworkDto setAuthor(HomeworkAuthorDto author) {
    this.author = author;
    return this;
  }

  @JsonProperty("authorId")
  public void setAuthorId(Integer id) {
    this.author = new HomeworkAuthorDto(id);
  }

  public HomeworkDto setLecture(HomeworkLectureDto lecture) {
    this.lecture = lecture;
    return this;
  }

  @JsonProperty("lectureId")
  public void setLectureId(Integer id) {
    this.lecture = new HomeworkLectureDto(id);
  }

  public HomeworkDto setRepositoryLink(String repositoryLink) {
    this.repositoryLink = repositoryLink;
    return this;
  }

  public HomeworkDto setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public HomeworkDto setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
    return this;
  }

  public HomeworkDto setReviewDuration(Integer reviewDuration) {
    this.reviewDuration = reviewDuration;
    return this;
  }



  public Integer getId() {
    return id;
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

  public HomeworkAuthorDto getAuthor() {
    return author;
  }

  public HomeworkLectureDto getLecture() {
    return lecture;
  }

  public String getRepositoryLink() {
    return repositoryLink;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getCompletionDeadline() {
    return completionDeadline;
  }

  public Integer getReviewDuration() {
    return reviewDuration;
  }

}
