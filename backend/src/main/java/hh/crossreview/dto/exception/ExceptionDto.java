package hh.crossreview.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(name = "Exception")
public class ExceptionDto {

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Date timestamp;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer status;
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String message;

  public ExceptionDto(Integer status, String message) {
    this.timestamp = new Date();
    this.status = status;
    this.message = message;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public Integer getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

}
