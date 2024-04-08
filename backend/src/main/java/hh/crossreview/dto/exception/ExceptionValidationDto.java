package hh.crossreview.dto.exception;

import java.util.List;

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
