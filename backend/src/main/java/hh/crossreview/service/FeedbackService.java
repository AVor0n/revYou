package hh.crossreview.service;

import hh.crossreview.converter.FeedbackConverter;
import hh.crossreview.dao.FeedbackDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.feedback.FeedbackDto;
import hh.crossreview.dto.feedback.FeedbackPostDto;
import hh.crossreview.dto.feedback.FeedbackPostResponseDto;
import hh.crossreview.entity.Feedback;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import java.util.List;
import java.util.Objects;

@Named
@Singleton
public class FeedbackService extends GenericService {
  private final FeedbackDao feedbackDao;
  private final FeedbackConverter feedbackConverter;

  public FeedbackService(FeedbackDao feedbackDao, FeedbackConverter feedbackConverter, UserDao userDao) {
    super(userDao);
    this.feedbackDao = feedbackDao;
    this.feedbackConverter = feedbackConverter;
  }

  @Transactional
  public FeedbackDto getFeedback(Integer id) {
    var feedback = feedbackDao.find(Feedback.class, id);
    requireEntityNotNull(feedback, String.format("Feedback with id %d was not found", id));
    return feedbackConverter.convertToFeedbackDto(feedback);
  }

  @Transactional
  public List<FeedbackDto> getFeedbacks() {
    var feedback = feedbackDao.getFeedBacks();
    requireEntityNotNull(feedback, String.format("Feedback with id %d was not found", 1));
    return feedbackConverter.convertToFeedbacksWrapperDto(feedback);
  }

  @Transactional
  public FeedbackPostResponseDto createFeedback(FeedbackPostDto feedbackPostDto, User student) {
    var review = feedbackDao.find(Review.class, feedbackPostDto.getReviewId());
    requireStudentRequestedReview(student, review);
    var feedback = feedbackConverter.convertToFeedback(feedbackPostDto, review, student);
    feedbackDao.save(feedback);
    return feedbackConverter.convertToFeedbackPostResponseDto(feedback.getFeedbackId());
  }

  private void requireStudentRequestedReview(User student, Review review) {
    if (!Objects.equals(review.getStudent().getUserId(), student.getUserId())) {
      throw new ForbiddenException("Action forbidden");
    }
  }

}
