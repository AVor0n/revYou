package hh.crossreview.converter;

import hh.crossreview.dto.review.ReviewAttemptDto;
import hh.crossreview.dto.review.ReviewDto;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Named
@Singleton
public class ReviewConverter {

  public ReviewDto convertToReviewDto(Review review) {
    ReviewDto reviewDto = new ReviewDto()
        .setReviewId(review.getReviewId())
        .setStudentId(review.getStudent().getUserId())
        .setStatus(review.getStatus().toString())
        .setSolutionId(review.getSolution().getSolutionId());

    List<ReviewAttempt> reviewAttempts = review.getReviewAttempts();
    if (reviewAttempts != null) {
      reviewDto.setReviewAttempts(
              reviewAttempts
                      .stream()
                      .map(this::convertToReviewAttemptDto)
                      .toList()
      );
    } else {
      reviewDto.setReviewAttempts(Collections.emptyList());
    }

    User reviewer = review.getReviewer();
    if (reviewer != null) {
      reviewDto.setReviewerId(reviewer.getUserId());
    }
    return reviewDto;
  }

  public ReviewAttemptDto convertToReviewAttemptDto(ReviewAttempt reviewAttempt) {
    return new ReviewAttemptDto()
        .setReviewAttemptId(reviewAttempt.getReviewAttemptId())
        .setReviewId(reviewAttempt.getReview().getReviewId())
        .setSolutionAttemptId(reviewAttempt.getSolutionAttempt().getSolutionAttemptId())
        .setCreatedAt(reviewAttempt.getCreatedAt())
        .setFinishedAt(reviewAttempt.getFinishedAt())
        .setResolution(reviewAttempt.getResolution());
  }
}
