package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.GetAllHomeworksWrapper;
import hh.crossreview.entity.Homework;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Named
@Singleton
public class HomeworkService {

  private final HomeworkDao homeworkDao;
  private final HomeworkConverter homeworkConverter;

  public HomeworkService(HomeworkDao homeworkDao, HomeworkConverter homeworkConverter) {
    this.homeworkDao = homeworkDao;
    this.homeworkConverter = homeworkConverter;
  }

  @Transactional
  public GetAllHomeworksWrapper getHomeworks() {
    List<Homework> homeworks = homeworkDao.getHomeworks();
    return homeworkConverter.homeworksToGetAllHomeworksWrapper(homeworks);
  }

}
