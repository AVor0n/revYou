package com.example.backend.converter;

import com.example.backend.dto.homework.GetHomeworkDto;
import com.example.backend.entity.Homework;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Named
@Singleton
public class HomeworkConverter {

  private final ModelMapper modelMapper;

  public HomeworkConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public GetHomeworkDto homeworkToGetHomeworkDto(Homework homework) {
    return modelMapper.map(homework, GetHomeworkDto.class);
  }

}
