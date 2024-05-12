package hh.crossreview.dao;

import hh.crossreview.entity.Cohort;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

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
