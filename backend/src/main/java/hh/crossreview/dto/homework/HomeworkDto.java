package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;

@Schema(
    name = "Homework",
    requiredProperties = {
        "id", "name", "topic",
        "departments", "author",
        "lecture", "repositoryLink",
        "startDate", "completionDeadline",
        "reviewDuration"
    }
)
@SuppressWarnings({"unused"})
public class HomeworkDto {

  private Integer id;
  private String name;
  private String topic;
  private String description;
  private List<String> departments;
  private HomeworkAuthorDto author;
  private HomeworkLectureDto lecture;
  private String repositoryLink;
  private Date startDate;
  private Date completionDeadline;

  @Schema(allowableValues =  {"24", "48"})
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

  public HomeworkDto setLecture(HomeworkLectureDto lecture) {
    this.lecture = lecture;
    return this;
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
