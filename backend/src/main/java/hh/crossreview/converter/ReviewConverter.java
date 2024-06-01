package hh.crossreview.converter;

import hh.crossreview.dto.review.ReviewAttemptDto;
import hh.crossreview.dto.review.ReviewDto;
import hh.crossreview.dto.review.ReviewWrapperDto;
import hh.crossreview.dto.review.info.ReviewInfoDto;
import hh.crossreview.dto.review.info.ReviewInfoWrapperDto;
import hh.crossreview.dto.user.info.StudentDto;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.InternalServerErrorException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;

@Named
@Singleton
public class ReviewConverter {

  public ReviewDto convertToReviewDto(
          Review review,
          String sourceCommitId,
          String commitId) {
    ReviewDto reviewDto = new ReviewDto()
        .setReviewId(review.getReviewId())
        .setStatus(review.getStatus())
        .setCommitId(commitId)
        .setProjectId(review.getSolution().getProjectId())
        .setSourceCommitId(sourceCommitId);

    List<ReviewAttempt> reviewAttempts = review.getReviewAttempts();
    reviewDto.setReviewAttempts(convertToReviewAttemptsDto(reviewAttempts));
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
          String sourceCommitId) {
    List<ReviewDto> reviewsDto = reviews
            .stream()
            .map((Review review) -> convertToReviewDto(review, sourceCommitId, commitId))
            .toList();
    return new ReviewWrapperDto(reviewsDto);
  }


  public void setStatus(Review review, ReviewStatus status) {
    review.setStatus(status);
  }

  public void setReviewer(Review review, User reviewer) {
    review.setReviewer(reviewer);
  }

  public ReviewWrapperDto convertToReviewWrapperDto(List<ImmutablePair<Review, String>> pairsReviewCommit, String sourceCommitId) {
    List<ReviewDto> reviewsDto = pairsReviewCommit
        .stream()
        .map(reviewCommit -> convertToReviewDto(
            reviewCommit.getKey(),
                sourceCommitId,
            reviewCommit.getValue()))
        .toList();
    return new ReviewWrapperDto(reviewsDto);
  }

  public ReviewInfoWrapperDto convertToReviewInfoWrapperDto(List<ImmutablePair<Review, String>> pairsReviewCommit, String sourceCommitId) {
    List<ReviewInfoDto> reviewsDto = pairsReviewCommit
        .stream()
        .map(reviewCommit -> convertToReviewInfoDto(
          reviewCommit.getKey(),
          reviewCommit.getValue()
        ))
        .toList();
    return new ReviewInfoWrapperDto(reviewsDto, sourceCommitId);
  }

  public ReviewInfoDto convertToReviewInfoDto(Review review, String commitId) {
    StudentDto student = new StudentDto(review.getStudent());
    Duration duration = countReviewDuration(review, review.getReviewAttempts());
    List<ReviewAttemptDto> reviewAttemptsDto = convertToReviewAttemptsDto(review.getReviewAttempts());
    ReviewInfoDto reviewInfoDto = new ReviewInfoDto()
        .setReviewAttempts(reviewAttemptsDto)
        .setDuration(duration)
        .setStudent(student)
        .setReviewId(review.getReviewId())
        .setStatus(review.getStatus().toString())
        .setCommitId(commitId)
        .setProjectId(review.getSolution().getProjectId());

    User reviewer = review.getReviewer();
    if (reviewer != null) {
      reviewInfoDto.setReviewer(new StudentDto(reviewer));
    }
    return reviewInfoDto;
  }

  private List<ReviewAttemptDto> convertToReviewAttemptsDto(List<ReviewAttempt> reviewAttempts) {
    if (reviewAttempts != null && !reviewAttempts.isEmpty()) {
      ReviewAttempt reviewAttempt = reviewAttempts.getLast();
      return List.of(convertToReviewAttemptDto(reviewAttempt));
    }
    return Collections.emptyList();
  }

  private Duration countReviewDuration(Review review, List<ReviewAttempt> reviewAttemptsDto) {
    if (reviewAttemptsDto.isEmpty()) {
      return Duration.ZERO;
    }

    LocalDateTime startDateTime = findStartDateTime(reviewAttemptsDto);
    LocalDateTime finishDateTime = review.getStatus().equals(ReviewStatus.APPROVED) ?
        findFinishDateTime(reviewAttemptsDto) :
        LocalDateTime.now();
    return Duration.between(startDateTime, finishDateTime);
  }

  private LocalDateTime findStartDateTime(List<ReviewAttempt> reviewAttemptsDto) {
    return reviewAttemptsDto.stream()
        .min(Comparator.comparing(ReviewAttempt::getCreatedAt))
        .orElseThrow(() -> new InternalServerErrorException("ReviewAttempt must have a creation date"))
        .getCreatedAt();
  }

  private LocalDateTime findFinishDateTime(List<ReviewAttempt> reviewAttemptsDto) {
    return reviewAttemptsDto.stream()
        .max(Comparator.comparing(ReviewAttempt::getCreatedAt))
        .orElseThrow(() -> new InternalServerErrorException("ReviewAttempt must have a finish date if review approved"))
        .getFinishedAt();
  }

}
