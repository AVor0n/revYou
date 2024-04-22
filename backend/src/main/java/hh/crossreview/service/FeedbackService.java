package hh.crossreview.service;

import hh.crossreview.converter.FeedbackConverter;
import hh.crossreview.dao.FeedbackDao;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.feedback.FeedbackDto;
import hh.crossreview.dto.feedback.FeedbackPostDto;
import hh.crossreview.dto.feedback.FeedbackPostResponseDto;
import hh.crossreview.entity.Feedback;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@Singleton
public class FeedbackService extends GenericService{
  private FeedbackDao feedbackDao;
  private FeedbackConverter feedbackConverter;

  public FeedbackService(FeedbackDao feedbackDao, FeedbackConverter feedbackConverter, UserDao userDao, HomeworkDao homeworkDao) {
    super(userDao);
    this.feedbackDao = feedbackDao;
   this.feedbackConverter = feedbackConverter;
  }

  @Transactional
  public FeedbackDto getFeedback(Integer id) {
    var feedback = feedbackDao.find(Feedback.class, id);
    requireEntityNotNull(feedback, String.format("Homework with id %d was not found", id));
    return feedbackConverter.convertToFeedbackDto(feedback);
  }

  @Transactional
  public List<FeedbackDto> getFeedbacks() {
    var feedback = feedbackDao.getFeedBacks();
    requireEntityNotNull(feedback, String.format("Homework with id %d was not found", 1));
    return feedbackConverter.convertToFeedbacksWrapperDto(feedback);
  }

  @Transactional
  public FeedbackPostResponseDto createFeedback(FeedbackPostDto feedbackPostDto, UsernamePasswordAuthenticationToken token) {
    var reviewer = retrieveUserFromToken(token);
    var student = feedbackDao.find(User.class, feedbackPostDto.getStudent());
    var feedback = feedbackConverter.convertToFeedback(feedbackPostDto, reviewer,student);
    feedbackDao.save(feedback);
    return feedbackConverter.convertToFeedbackPostResponseDto(feedback.getFeedbackId());
  }

}
