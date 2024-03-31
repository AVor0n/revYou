package hh.crossreview.configuration;

import hh.crossreview.dto.homework.GetHomeworkDto;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import java.util.Collections;
import java.util.List;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  @Bean("modelMapper")
  public ModelMapper getModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    modelMapper.createTypeMap(Homework.class, GetHomeworkDto.class)
        .addMappings(mapper -> {
          mapper.<Integer>map(
              src -> src.getLecture().getTeacher().getUserId(),
              (dest, v) -> dest.getAuthor().setId(v));
          mapper.<String>map(
              src -> src.getLecture().getTeacher().getName(),
              (dest, v) -> dest.getAuthor().setFirstName(v));
          mapper.<String>map(
              src -> src.getLecture().getTeacher().getSurname(),
              (dest, v) -> dest.getAuthor().setLastName(v));
        })
        .addMappings(mapper -> mapper
            .using(cohortsToDepartments)
            .map(
                src -> src.getLecture().getCohorts(),
                GetHomeworkDto::setDepartments));
    return modelMapper;
  }

  Converter<List<Cohort>, List<String>> cohortsToDepartments =
      ctx -> ctx.getSource() == null ? Collections.emptyList() : ctx
          .getSource()
          .stream()
          .map(cohort -> cohort.getStudyDirection().toString())
          .toList();
}
