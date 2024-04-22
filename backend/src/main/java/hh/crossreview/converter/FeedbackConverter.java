package hh.crossreview.converter;

import hh.crossreview.dto.feedback.FeedbackDto;
import hh.crossreview.dto.feedback.FeedbackPostDto;
import hh.crossreview.dto.feedback.FeedbackPostResponseDto;
import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.Feedback;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;

@Named
@Singleton
public class FeedbackConverter {
  public List<FeedbackDto> convertToFeedbacksWrapperDto(List<Feedback> feedbacks) {
    return feedbacks.stream().map(this::convertToFeedbackDto).toList();
  }

  public FeedbackDto convertToFeedbackDto(Feedback feedback) {
    return new FeedbackDto().setFeedbackId(feedback.getFeedbackId()).setReviewer(converToUserDto(feedback.getReviewer()))
        .setStudent(converToUserDto(feedback.getStudent())).
        setDescription(feedback.getDescription()).setRating(feedback.getRating())
        .setFeedbackDate(feedback.getFeedbackDate());
  }

  private UserDto converToUserDto(User user) {
    return new UserDto().setUserId(user.getUserId()).setUsername(user.getUsername())
        .setEmail(user.getEmail());
  }

  public Feedback convertToFeedback(FeedbackPostDto feedbackPostDto, User reviwer, User student) {
    return new Feedback().setReviewer(reviwer).setStudent(student)
        .setDescription(feedbackPostDto.getDescription()).setRating(feedbackPostDto.getRating()).setFeedbackDate(
            LocalDateTime.now());
  }
  public FeedbackPostResponseDto convertToFeedbackPostResponseDto(Integer feedbackId) {
    return new FeedbackPostResponseDto(feedbackId);
  }

}
