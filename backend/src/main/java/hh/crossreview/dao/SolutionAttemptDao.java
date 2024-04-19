package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
@SuppressWarnings({"unused"})
public class SolutionAttemptDao extends GenericDao {

  public List<SolutionAttempt> findByHomeworkAndStudent(Homework homework, User student) {
    return getEntityManager()
        .createQuery("SELECT sa FROM SolutionAttempt sa " +
            "WHERE sa.solution.homework = :homework " +
            "AND sa.solution.student = :student " +
            "ORDER BY sa.createdAt",
            SolutionAttempt.class)
        .setParameter("homework", homework)
        .setParameter("student", student)
        .getResultList();
  }

}
