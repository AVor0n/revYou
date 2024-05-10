package hh.crossreview.converter;

import hh.crossreview.dto.review.ReviewAttemptDto;
import hh.crossreview.dto.review.ReviewDto;
import hh.crossreview.dto.review.ReviewWrapperDto;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Named
@Singleton
public class ReviewConverter {

  public ReviewDto convertToReviewDto(
          Review review,
          Integer projectId,
          String sourceCommitId,
          String commitId) {
    ReviewDto reviewDto = new ReviewDto()
        .setReviewId(review.getReviewId())
        .setStatus(review.getStatus().toString())
        .setCommitId(commitId)
        .setProjectId(projectId)
        .setSourceCommitId(sourceCommitId);

    List<ReviewAttempt> reviewAttempts = review.getReviewAttempts();
    if (reviewAttempts != null && !reviewAttempts.isEmpty()) {
      ReviewAttempt reviewAttempt = reviewAttempts.getLast();
      reviewDto.setReviewAttempts(
          List.of(convertToReviewAttemptDto(reviewAttempt))
      );
    } else {
      reviewDto.setReviewAttempts(Collections.emptyList());
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

  public ReviewWrapperDto convertToReviewWrapperDto(
          List<Review> reviews,
          String commitId,
          Integer projectId,
          String sourceCommitId) {
    List<ReviewDto> reviewsDto = reviews
            .stream()
            .map((Review review) -> convertToReviewDto(review, projectId, sourceCommitId, commitId))
            .toList();
    return new ReviewWrapperDto(reviewsDto);
  }


  public void setStatus(Review review, ReviewStatus status) {
    review.setStatus(status);
  }

  public void setReviewer(Review review, User reviewer) {
    review.setReviewer(reviewer);
  }
}
