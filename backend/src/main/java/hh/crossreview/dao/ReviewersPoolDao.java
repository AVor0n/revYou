package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Reviewer;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewerStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Optional;

@Named
@Singleton
public class ReviewersPoolDao extends GenericDao {

  public Optional<Reviewer> findByUserAndHomework(User user, Homework homework) {
    return getEntityManager()
        .createQuery(
            "SELECT pr FROM Reviewer pr " +
            "WHERE pr.user = :user " +
            "AND pr.homework = :homework ",
            Reviewer.class)
        .setParameter("user", user)
        .setParameter("homework", homework)
        .getResultStream()
        .findFirst();
  }

  public Optional<Reviewer> findAvailableReviewer(Homework homework) {
    return getEntityManager()
        .createQuery(
            "SELECT pr FROM Reviewer pr " +
                "WHERE pr.homework = :homework " +
                "AND pr.status = :status " +
                "ORDER BY pr.appointedAt " +
                "LIMIT 1",
            Reviewer.class)
        .setParameter("homework", homework)
        .setParameter("status", ReviewerStatus.AVAILABLE)
        .getResultStream()
        .findFirst();
  }

}
