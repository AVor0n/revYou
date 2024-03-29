package com.example.backend.dto.homework;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class GetHomeworkDto {

  @JsonProperty("id")
  private Integer homeworkId;

  @JsonProperty("name")
  private String title;

  @JsonProperty("topic")
  private String theme;

  @JsonProperty("departments")
  private List<String> departments;

  private GetHomeworkAuthorDto author;

  private String homeworkLink;

  @JsonProperty("startDate")
  private Date creationDate;

  private Date completionDeadline;

  private Date reviewDeadline;

  public Integer getHomeworkId() {
    return homeworkId;
  }

  public void setHomeworkId(Integer homeworkId) {
    this.homeworkId = homeworkId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public List<String> getDepartments() {
    return departments;
  }

  public void setDepartments(List<String> departments) {
    this.departments = departments;
  }

  public GetHomeworkAuthorDto getAuthor() {
    return author;
  }

  public void setAuthor(GetHomeworkAuthorDto author) {
    this.author = author;
  }

  public String getHomeworkLink() {
    return homeworkLink;
  }

  public void setHomeworkLink(String homeworkLink) {
    this.homeworkLink = homeworkLink;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getCompletionDeadline() {
    return completionDeadline;
  }

  public void setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
  }

  public Date getReviewDeadline() {
    return reviewDeadline;
  }

  public void setReviewDeadline(Date reviewDeadline) {
    this.reviewDeadline = reviewDeadline;
  }

}
