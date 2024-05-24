package hh.crossreview.dao;

import hh.crossreview.entity.CommentsThread;
import hh.crossreview.entity.Review;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class CommentsThreadDao extends GenericDao{
  public List<CommentsThread> findByReview(Review review){
    return getEntityManager()
        .createQuery(
    "SELECT t FROM CommentsThread t " +
            "WHERE t.review = :review",
             CommentsThread.class
        )
            .setParameter("review", review)
            .getResultStream()
            .toList();
  }

  public void deleteCommentsThread(CommentsThread commentsThread){
    getEntityManager().remove(commentsThread);
  }

}
