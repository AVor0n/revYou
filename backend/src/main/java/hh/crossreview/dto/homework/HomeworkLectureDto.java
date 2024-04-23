package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HomeworkLecture", requiredProperties = {"id", "name"})
public class HomeworkLectureDto {

  private Integer id;
  private String name;

  public HomeworkLectureDto(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public HomeworkLectureDto(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
