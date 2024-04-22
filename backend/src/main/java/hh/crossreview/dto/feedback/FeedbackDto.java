package hh.crossreview.dto.feedback;

import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.Feedback;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Feedback}
 */
public class FeedbackDto implements Serializable {

  private Integer feedbackId;
  private UserDto reviewer;
  private UserDto student;
  private String description;
  private Integer rating;
  private LocalDateTime feedbackDate;

  public FeedbackDto() {
    this.feedbackId = null;
    this.reviewer = null;
    this.student = null;
    this.description = null;
    this.rating = null;
    this.feedbackDate = null;
  }

  public Integer getFeedbackId() {
    return feedbackId;
  }

  public FeedbackDto setFeedbackId(Integer feedbackId) {
    this.feedbackId = feedbackId;
    return this;
  }

  public UserDto getReviewer() {
    return reviewer;
  }

  public FeedbackDto setReviewer(UserDto reviewer) {
    this.reviewer = reviewer;
    return this;
  }

  public UserDto getStudent() {
    return student;
  }

  public FeedbackDto setStudent(UserDto student) {
    this.student = student;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public FeedbackDto setDescription(String description) {
    this.description = description;
    return this;
  }

  public Integer getRating() {
    return rating;
  }

  public FeedbackDto setRating(Integer rating) {
    this.rating = rating;
    return this;
  }

  public LocalDateTime getFeedbackDate() {
    return feedbackDate;
  }

  public FeedbackDto setFeedbackDate(LocalDateTime feedbackDate) {
    this.feedbackDate = feedbackDate;
    return this;
  }

}