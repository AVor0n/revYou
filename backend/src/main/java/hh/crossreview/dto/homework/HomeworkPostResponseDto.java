package hh.crossreview.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HomeworkPostResponse", requiredProperties = {"id"})
@SuppressWarnings({"FieldMayBeFinal"})
public class HomeworkPostResponseDto {

  private Integer id;

  public Integer getId() {
    return id;
  }

  public HomeworkPostResponseDto(Integer id) {
    this.id = id;
  }

}
