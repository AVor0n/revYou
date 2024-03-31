package hh.crossreview.service;

import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dto.homework.GetHomeworkDto;
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
  public List<GetHomeworkDto> getHomeworks() {
    return homeworkDao.getHomeworks()
        .stream()
        .map(homeworkConverter::homeworkToGetHomeworkDto)
        .toList();
  }

}
