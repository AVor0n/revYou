package hh.crossreview.dao;

import hh.crossreview.entity.Review;
import hh.crossreview.entity.ReviewAttempt;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class ReviewAttemptDao extends GenericDao {
  public ReviewAttempt findLastReviewAttemptByReview(Review review){
    return getEntityManager()
            .createQuery(
                    "SELECT r FROM ReviewAttempt r " +
                            "WHERE r.review = :review",
                    ReviewAttempt.class)
            .setParameter("review", review)
            .getResultList()
            .getLast();
  }
}
