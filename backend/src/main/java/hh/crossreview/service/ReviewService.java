package hh.crossreview.service;

import hh.crossreview.converter.ReviewConverter;
import hh.crossreview.dao.ReviewDao;
import hh.crossreview.dto.review.ReviewDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import java.util.Optional;

@Named
@Singleton
public class ReviewService {
  private final RequirementsUtils reqUtils;

  private final SolutionService solutionService;

  private final ReviewDao reviewDao;

  private final ReviewConverter reviewConverter;

  public ReviewService(RequirementsUtils reqUtils, SolutionService solutionService, ReviewDao reviewDao, ReviewConverter reviewConverter) {
    this.reqUtils = reqUtils;
    this.solutionService = solutionService;
    this.reviewDao = reviewDao;
    this.reviewConverter = reviewConverter;
  }


  @Transactional
  public ReviewDto createReview(Homework homework, User user){
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    Solution solution = solutionService.requireSolutionExist(homework, user);
    reqUtils.requireEntityHasStatus(solution, String.valueOf(SolutionStatus.REVIEW_STAGE));
    requireReviewInProgressAlreadyExist(solution, user);
    Review review = new Review(user, solution);
    reviewDao.save(review);
    return reviewConverter.convertToReviewDto(review);
  }

  public void requireReviewInProgressAlreadyExist(Solution solution, User student){
    Optional<Review> reviewsInProgress =  reviewDao.findBySolutionStudent(solution, student)
            .stream()
            .filter(review -> ReviewStatus.getInProgressStatuses().contains(review.getStatus()))
            .findFirst();
    if (reviewsInProgress.isPresent()) {
      throw new ForbiddenException("You already have active reviews for this homework");
    }
  }
}

