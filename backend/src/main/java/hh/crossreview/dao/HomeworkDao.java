package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class HomeworkDao extends GenericDao {

  public List<Homework> getHomeworks() {
    return getEntityManager()
        .createQuery(
            "SELECT h FROM Homework h ",
            Homework.class)
        .getResultList();
  }

  public void createHomework(Homework homework) {
    getEntityManager().persist(homework);
  }

  public void deleteHomework(Homework homework) {
    getEntityManager().remove(homework);
  }

}
