package hh.crossreview.dto.homework;

import hh.crossreview.entity.enums.SolutionStatus;
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

  private String sourceCommitId;

  private List<String> departments;

  private HomeworkAuthorDto author;

  private HomeworkLectureDto lecture;

  private String repositoryLink;

  private Date startDate;

  private Date completionDeadline;

  private SolutionStatus solution;

  @Schema(allowableValues = {"24", "48"})
  private Integer reviewDuration;

  public Integer getId() {
    return id;
  }

  public HomeworkDto setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public HomeworkDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getTopic() {
    return topic;
  }

  public HomeworkDto setTopic(String topic) {
    this.topic = topic;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public HomeworkDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getSourceCommitId() {
    return sourceCommitId;
  }

  public HomeworkDto setSourceCommitId(String sourceCommitId) {
    this.sourceCommitId = sourceCommitId;
    return this;
  }

  public List<String> getDepartments() {
    return departments;
  }

  public HomeworkDto setDepartments(List<String> departments) {
    this.departments = departments;
    return this;
  }

  public HomeworkAuthorDto getAuthor() {
    return author;
  }

  public HomeworkDto setAuthor(HomeworkAuthorDto author) {
    this.author = author;
    return this;
  }

  public HomeworkLectureDto getLecture() {
    return lecture;
  }

  public HomeworkDto setLecture(HomeworkLectureDto lecture) {
    this.lecture = lecture;
    return this;
  }

  public String getRepositoryLink() {
    return repositoryLink;
  }

  public HomeworkDto setRepositoryLink(String repositoryLink) {
    this.repositoryLink = repositoryLink;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public HomeworkDto setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Date getCompletionDeadline() {
    return completionDeadline;
  }

  public HomeworkDto setCompletionDeadline(Date completionDeadline) {
    this.completionDeadline = completionDeadline;
    return this;
  }

  public Integer getReviewDuration() {
    return reviewDuration;
  }

  public HomeworkDto setReviewDuration(Integer reviewDuration) {
    this.reviewDuration = reviewDuration;
    return this;
  }

  public SolutionStatus getSolution() {
    return solution;
  }

  public HomeworkDto setSolution(SolutionStatus solution) {
    this.solution = solution;
    return this;
  }

}
