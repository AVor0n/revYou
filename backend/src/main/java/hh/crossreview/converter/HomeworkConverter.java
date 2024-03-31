package hh.crossreview.converter;

import hh.crossreview.dto.homework.GetHomeworkDto;
import hh.crossreview.entity.Homework;
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
