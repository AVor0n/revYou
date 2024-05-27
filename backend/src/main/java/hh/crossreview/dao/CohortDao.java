package hh.crossreview.dao;

import jakarta.inject.Named;

@Named
@Singleton
public class CohortDao extends GenericDao {

  public List<Cohort> findAll() {
    return getEntityManager()
        .createQuery(
            "SELECT c FROM Cohort c ",
            Cohort.class)
        .getResultList();
  }
}
