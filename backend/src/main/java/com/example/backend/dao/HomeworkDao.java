package com.example.backend.dao;

import com.example.backend.entity.Homework;
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

}
