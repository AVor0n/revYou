package hh.crossreview.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(name = "Exception", requiredProperties = {"timestamp", "status", "message"})
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ExceptionDto {

  private Date timestamp;
  private Integer status;
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
