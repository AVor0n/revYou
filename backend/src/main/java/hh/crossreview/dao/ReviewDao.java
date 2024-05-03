package hh.crossreview.dao;

import hh.crossreview.entity.Review;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;


@Named
@Singleton
public class ReviewDao extends GenericDao {
  public List<Review> findBySolutionStudent(Solution solution, User student) {
    return getEntityManager()
        .createQuery("SELECT r FROM Review r " +
            "WHERE r.solution = :solution " +
            "AND r.student = :student ",
            Review.class)
            .setParameter("solution", solution)
            .setParameter("student", student)
            .getResultStream()
            .toList();
  }
}
