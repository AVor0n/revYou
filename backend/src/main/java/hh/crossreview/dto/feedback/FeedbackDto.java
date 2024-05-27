package hh.crossreview.dto.feedback;

import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.Feedback;
import java.io.Serializable;
import java.time.LocalDateTime;

public class FeedbackDto implements Serializable {

  private Integer feedbackId;
  private Integer reviewId;
  private UserDto student;
  private String description;
  private Integer rating;
  private LocalDateTime feedbackDate;

  public Integer getFeedbackId() {
    return feedbackId;
  }

  public FeedbackDto setFeedbackId(Integer feedbackId) {
    this.feedbackId = feedbackId;
    return this;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public FeedbackDto setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
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
