package hh.crossreview.unit.converter;

import hh.crossreview.converter.ReviewConverter;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.unit.TestsUtil;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ReviewConverterTests extends TestsUtil {

  private final ReviewConverter reviewConverter = new ReviewConverter();

  @Test
  void givenReviewCommitPairs_whenConvertToReviewInfoWrapperCall_thenReturnValidAnswer() {
    var subHours = 24;
    var subMinutes = 1;
    var finishedAt = LocalDateTime.now();
    var createdAt = finishedAt.minusHours(subHours).minusMinutes(subMinutes);
    var pairs = getReviewTestData(createdAt, finishedAt);

    var sourceCommitId = "sourceCommitId";
    var reviewInfoWrapperDto = reviewConverter.convertToReviewInfoWrapperDto(pairs, sourceCommitId);

    assertEquals(reviewInfoWrapperDto.getSourceCommitId(), sourceCommitId);
    assertEquals(1, reviewInfoWrapperDto.getData().size());
    assertEquals(1, reviewInfoWrapperDto.getData().getFirst().getReviewAttempts().size());
    assertEquals(24, reviewInfoWrapperDto.getData().getFirst().getDuration().getHours());
    assertEquals(1, reviewInfoWrapperDto.getData().getFirst().getDuration().getMinutes());
  }

  private List<ImmutablePair<Review, String>> getReviewTestData(LocalDateTime createdAt, LocalDateTime finishedAt) {
    var backHomework = createBackHomework();
    var student = createUser(2, UserRole.STUDENT, backHomework.getCohorts().getFirst());
    var reviewer = createUser(1, UserRole.STUDENT, backHomework.getCohorts().getFirst());
    var solution = createSolution(1, SolutionStatus.REVIEW_STAGE, student, 1, "branch", Collections.emptyList());
    var reviewAttempt1 = createReviewAttempt(1, createdAt, finishedAt, "Corrections required", createSolutionAttempt("", null), null);
    var reviewAttempt2 = createReviewAttempt(1, createdAt, finishedAt, "Approved", createSolutionAttempt("", null), null);
    var review = createReview(1, ReviewStatus.APPROVED, solution, List.of(reviewAttempt1, reviewAttempt2), reviewer, student);
    reviewAttempt1.setReview(review);
    reviewAttempt2.setReview(review);
    return List.of(new ImmutablePair<>(review, "commitId"));
  }

}
