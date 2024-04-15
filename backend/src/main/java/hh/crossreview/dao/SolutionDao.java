package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class SolutionDao extends GenericDao {

  public List<Solution> findByBranchLinkForOtherUser(String branchLink, User user) {
    return getEntityManager()
        .createQuery("SELECT s FROM Solution s " +
            "WHERE s.branchLink = :branchLink " +
            "AND s.author != :user",
            Solution.class)
        .setParameter("branchLink", branchLink)
        .setParameter("user", user)
        .getResultList();
  }

  public Solution findLastSolutionByHomeworkAndUser(Homework homework, User user) {
    return getEntityManager()
        .createQuery("SELECT s FROM Solution s " +
            "WHERE s.homework = :homework " +
            "AND s.author = :user " +
            "ORDER BY attemptNumber DESC " +
            "LIMIT 1",
            Solution.class)
        .setParameter("homework", homework)
        .setParameter("user", user)
        .getSingleResult();
  }

}
