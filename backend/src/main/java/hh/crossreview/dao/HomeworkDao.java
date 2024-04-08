package hh.crossreview.dao;

import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class HomeworkDao extends GenericDao {

  public List<Homework> findAll() {
    return getEntityManager()
        .createQuery(
            "SELECT h FROM Homework h ",
            Homework.class)
        .getResultList();
  }

  public List<Homework> findByAuthor(User author) {
    return getEntityManager()
        .createQuery(
            "SELECT h FROM Homework h " +
                "WHERE h.author = :author",
            Homework.class)
        .setParameter("author", author)
        .getResultList();
  }

  public List<Homework> findByCohort(Cohort cohort) {
    List<Lecture> lectures = findLecturesByCohort(cohort);
    return getEntityManager()
        .createQuery(
            "SELECT h FROM Homework h " +
                "WHERE h.lecture IN :lectures",
            Homework.class)
        .setParameter("lectures", lectures)
        .getResultList();
  }

  private List<Lecture> findLecturesByCohort(Cohort cohort) {
    return getEntityManager()
        .createQuery(
            "SELECT l FROM Lecture l " +
                "WHERE :cohort MEMBER OF l.cohorts",
            Lecture.class)
        .setParameter("cohort", cohort)
        .getResultList();
  }

  public void deleteHomework(Homework homework) {
    getEntityManager().remove(homework);
  }

}
