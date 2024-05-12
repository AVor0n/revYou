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

  public List<Review> findReviewsByHomeworkAndStatus(Homework homework, ReviewStatus reviewStatus, int maxResult) {
    return getEntityManager()
        .createQuery(
            "SELECT r FROM Review r " +
                "JOIN r.solution s " +
                "WHERE s.homework = :homework " +
                "AND r.status = :status ",
            Review.class
        )
        .setParameter("homework", homework)
        .setParameter("status", reviewStatus)
        .setMaxResults(maxResult)
        .getResultList();
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

  public Long countApprovedReviewsByHomework(Homework homework) {
    return getEntityManager()
        .createQuery(
            "SELECT COUNT(*) FROM Review r " +
                "JOIN r.solution s " +
                "WHERE s.homework = :homework " +
                "AND r.status = :status ",
            Long.class
        )
        .setParameter("homework", homework)
        .setParameter("status", ReviewStatus.APPROVED)
        .getSingleResult();
  }
}
