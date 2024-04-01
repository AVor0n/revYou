package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import org.hibernate.SessionFactory;

@Named
@Singleton
public class HomeworkDao extends GenericDao {

  public HomeworkDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Homework> getHomeworks() {
    return getSession()
        .createQuery(
            "SELECT h FROM Homework h " +
            "JOIN FETCH h.lecture " +
            "JOIN FETCH h.lecture.cohorts " +
            "JOIN FETCH h.lecture.teacher",
            Homework.class)
        .getResultList();
  }

  public void createHomework(Homework homework) {
    getSession().persist(homework);
    getSession().flush();
  }
}
