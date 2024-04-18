package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Named
@Singleton
public class SolutionDao extends GenericDao {

  public Optional<Solution> findByBranchLink(String branchLink) {
    return getEntityManager()
        .createQuery("SELECT s FROM Solution s " +
            "WHERE s.branchLink = :branchLink ",
            Solution.class)
        .setParameter("branchLink", branchLink)
        .getResultStream()
        .findFirst();
  }

  public Optional<Solution> findByHomeworkAndStudent(Homework homework, User student) {
    return getEntityManager()
        .createQuery("SELECT s FROM Solution s " +
                "WHERE s.homework = :homework " +
                "AND s.student = :student ",
            Solution.class)
        .setParameter("homework", homework)
        .setParameter("student", student)
        .getResultStream()
        .findFirst();
  }

  public List<Solution> findByHomework(Homework homework) {
    return getEntityManager()
        .createQuery("SELECT s FROM Solution s " +
            "WHERE s.homework = :homework",
            Solution.class)
        .setParameter("homework", homework)
        .getResultList();
  }

}
