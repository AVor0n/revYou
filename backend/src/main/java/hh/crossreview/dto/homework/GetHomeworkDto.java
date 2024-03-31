package hh.crossreview.dto.homework;

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

  private String description;

  @JsonProperty("departments")
  private List<String> departments;

  private GetHomeworkAuthorDto author;

  private String homeworkLink;

  @JsonProperty("startDate")
  private Date creationDate;

  private Date completionDeadline;

  private Date reviewDeadline;

  public GetHomeworkDto setHomeworkId(Integer homeworkId) {
    this.homeworkId = homeworkId;
    return this;
  }

  public GetHomeworkDto setTitle(String title) {
    this.title = title;
    return this;
  }

  public GetHomeworkDto setTheme(String theme) {
    this.theme = theme;
    return this;
  }

  public GetHomeworkDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public GetHomeworkDto setDepartments(List<String> departments) {
    this.departments = departments;
    return this;
  }

  public GetHomeworkDto setAuthor(GetHomeworkAuthorDto author) {
    this.author = author;
    return this;
  }

  public GetHomeworkDto setHomeworkLink(String homeworkLink) {
    this.homeworkLink = homeworkLink;
    return this;
  }

  public GetHomeworkDto setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public GetHomeworkDto setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
    return this;
  }

  public GetHomeworkDto setReviewDeadline(Date reviewDeadline) {
    this.reviewDeadline = reviewDeadline;
    return this;
  }

  public Integer getHomeworkId() {
    return homeworkId;
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

  public List<String> getDepartments() {
    return departments;
  }

  public GetHomeworkAuthorDto getAuthor() {
    return author;
  }

  public String getHomeworkLink() {
    return homeworkLink;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public Date getCompletionDeadline() {
    return completionDeadline;
  }

  public Date getReviewDeadline() {
    return reviewDeadline;
  }
}
