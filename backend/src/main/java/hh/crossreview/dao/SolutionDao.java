package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.SolutionStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Named
@Singleton
public class SolutionDao extends GenericDao {

  public Optional<Solution> findByProjectIdAndBranch(Integer projectId, String branch) {
    return getEntityManager()
        .createQuery(
            "SELECT s FROM Solution s " +
                "WHERE s.projectId = :projectId " +
                "AND s.branch = :branch ",
            Solution.class
        )
        .setParameter("projectId", projectId)
        .setParameter("branch", branch)
        .getResultStream()
        .findFirst();
  }

  public Optional<Solution> findByHomeworkAndStudent(Homework homework, User student) {
    return getEntityManager()
        .createQuery(
            "SELECT s FROM Solution s " +
                "WHERE s.homework = :homework " +
                "AND s.student = :student ",
            Solution.class
        )
        .setParameter("homework", homework)
        .setParameter("student", student)
        .getResultStream()
        .findFirst();
  }

  public List<Solution> findByHomework(Homework homework) {
    return getEntityManager()
        .createQuery(
            "SELECT s FROM Solution s " +
                "WHERE s.homework = :homework",
            Solution.class
        )
        .setParameter("homework", homework)
        .getResultList();
  }

  public void deleteSolution(Solution solution) {
    getEntityManager().remove(solution);
  }

  public List<User> getStudentsBySolutionStatusAndHomeworkId(
          SolutionStatus solutionStatus, Integer homeworkId
  ){
    return getEntityManager()
            .createQuery(
                "SELECT s.student FROM Solution s " +
                        "WHERE s.homework.id = :homeworkId AND " +
                        "s.status = :status " +
                        "ORDER BY s.student.surname",
                    User.class
            )
            .setParameter("homeworkId", homeworkId)
            .setParameter("status", solutionStatus)
            .getResultList();
  }
}
