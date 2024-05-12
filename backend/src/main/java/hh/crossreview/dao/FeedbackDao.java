package hh.crossreview.dao;

import hh.crossreview.entity.Feedback;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
@Named
@Singleton
public class FeedbackDao extends GenericDao{
  public List<Feedback> getFeedBacks() {
    return getEntityManager()
        .createQuery(
            "SELECT f FROM Feedback f ",
           Feedback.class)
        .getResultList();
  }

  public void createFeedback(Feedback feedback){getEntityManager().persist(feedback);}

}
