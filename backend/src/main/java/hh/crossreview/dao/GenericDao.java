package hh.crossreview.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;

public class GenericDao {

  @PersistenceContext
  private EntityManager entityManager;
  public <T> T find(Class<T> clazz, Serializable id) {
    return getEntityManager().find(clazz, id);
  }

  public void save(Object object) {
    if (object == null) {
      return;
    }
    getEntityManager().persist(object);
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }

}