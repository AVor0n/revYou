package hh.crossreview.dto.exception;

import java.util.Date;
import java.util.List;

public class ErrorSeveralMessagesDto {
  private List<String> messages;
  private Date timestamp;

  public ErrorSeveralMessagesDto(List<String> messages) {
    this.messages = messages;
    this.timestamp = new Date();
  }

  public List<String> getMessages() {
    return messages;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}
