package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewStatus;
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

  public Long countInProgressReviewsByReviewerAndHomework(User reviewer, Homework homework) {
    return getEntityManager()
        .createQuery("SELECT COUNT(*) FROM Review r " +
            "JOIN r.solution s " +
            "WHERE r.reviewer = :reviewer " +
            "AND s.homework = :homework " +
            "AND r.status IN :reviewStatuses ",
            Long.class)
        .setParameter("reviewer", reviewer)
        .setParameter("homework", homework)
        .setParameter("reviewStatuses", ReviewStatus.getInProgressStatuses())
        .getSingleResult();
  }
}
