package hh.crossreview.dao;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.HomeworkRecord;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.HomeworkRecordStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class HomeworkRecordDao extends GenericDao {

  public List<HomeworkRecord> findByHomeworkAndStudent(Homework homework, User student) {
    return getEntityManager()
        .createQuery("SELECT hr FROM HomeworkRecord hr " +
            "WHERE hr.homework = :homework " +
            "AND hr.student = :student",
            HomeworkRecord.class)
        .setParameter("homework", homework)
        .setParameter("student", student)
        .getResultList();
  }

  public List<User> findStudentsByHomeworkAndStatus(Homework homework, HomeworkRecordStatus homeworkRecordStatus) {
    return getEntityManager()
        .createQuery("SELECT hr.student FROM HomeworkRecord hr " +
            "WHERE hr.homework = :homework " +
            "AND hr.status = :homeworkRecordStatus",
            User.class)
        .setParameter("homework", homework)
        .setParameter("homeworkRecordStatus", homeworkRecordStatus)
        .getResultList();
  }
}
