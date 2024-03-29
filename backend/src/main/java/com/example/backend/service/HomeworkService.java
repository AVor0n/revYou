package com.example.backend.service;

import com.example.backend.converter.HomeworkConverter;
import com.example.backend.dao.HomeworkDao;
import com.example.backend.dto.homework.GetHomeworkDto;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Named
@Singleton
public class HomeworkService {

  Logger logger = LoggerFactory.getLogger(HomeworkService.class);
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
