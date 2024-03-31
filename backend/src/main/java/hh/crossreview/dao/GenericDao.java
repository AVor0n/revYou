package hh.crossreview.dao;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericDao {

  private final SessionFactory sessionFactory;

  public GenericDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public <T> T get(Class<T> clazz, Serializable id) {
    return getSession().get(clazz, id);
  }

  public void save(Object object) {
    if (object == null) {
      return;
    }
    getSession().persist(object);
  }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

}
