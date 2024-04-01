package hh.crossreview.dto.homework;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import java.util.List;

@JsonPropertyOrder
public class GetHomeworkDto {

  private Integer id;

  private String name;

  private String topic;

  private String description;

  private List<String> departments;

  private GetHomeworkAuthorDto author;

  private String homeworkLink;

  private Date startDate;

  private Date completionDeadline;

  private Date reviewDeadline;

  public GetHomeworkDto setId(Integer id) {
    this.id = id;
    return this;
  }

  public GetHomeworkDto setName(String name) {
    this.name = name;
    return this;
  }

  public GetHomeworkDto setTopic(String topic) {
    this.topic = topic;
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

  public GetHomeworkDto setStartDate(Date startDate) {
    this.startDate = startDate;
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

  public GetHomeworkAuthorDto getAuthor() {
    return author;
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

}
