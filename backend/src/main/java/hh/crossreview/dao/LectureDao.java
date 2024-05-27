package hh.crossreview.dao;

import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import java.util.List;

@Named
public class LectureDao extends GenericDao {
  public Lecture getLecture(Integer lectureId) {
    return getEntityManager().find(Lecture.class, lectureId);
  }

  public void deleteLecture(Lecture lecture) {
    getEntityManager().remove(lecture);
  }

  public List<Lecture> findLecturesByAuthor(User author) {
    return getEntityManager()
        .createQuery("SELECT l FROM Lecture l WHERE l.lector = :author", Lecture.class)
        .setParameter("author", author)
        .getResultList();
  }
}
