package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
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

  public List<Review> findByHomeworkAndStudent(Homework homework, User student) {
    return getEntityManager()
            .createQuery("SELECT r FROM Review r " +
                    "JOIN r.solution s " +
                    "JOIN r.student u " +
                    "WHERE s.homework = :homework " +
                    "AND u = :student", Review.class)
            .setParameter("homework", homework)
            .setParameter("student", student)
            .getResultStream()
            .toList();
  }

  public List<Review> findByHomeworkAndReviewer(Homework homework, User reviewer) {
    return getEntityManager()
            .createQuery(
                "SELECT r FROM Review r " +
                "JOIN r.solution s " +
                "JOIN r.reviewer u " +
                "WHERE s.homework = :homework " +
                "AND u = :reviewer", Review.class)
            .setParameter("homework", homework)
            .setParameter("reviewer", reviewer)
            .getResultStream()
            .toList();
  }
}
