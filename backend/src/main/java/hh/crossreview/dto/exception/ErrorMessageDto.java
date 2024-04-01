package hh.crossreview.dto.exception;

import java.util.Date;

public class ErrorMessageDto {

  private String message;
  private Date timestamp;

  public ErrorMessageDto(Exception e) {
    this.message = e.getMessage();
    this.timestamp = new Date();
  }

  public String getMessage() {
    return message;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}
