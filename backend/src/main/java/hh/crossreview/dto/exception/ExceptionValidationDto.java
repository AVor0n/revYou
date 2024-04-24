package hh.crossreview.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
    name = "ExceptionValidation",
    requiredProperties = {"timestamp", "status", "message", "validationMessages"}
)
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ExceptionValidationDto extends ExceptionDto {

  private List<String> validationMessages;

  public ExceptionValidationDto(Integer status, String message, List<String> validationMessages) {
    super(status, message);
    this.validationMessages = validationMessages;
  }

  public List<String> getValidationMessages() {
    return validationMessages;
  }
}
