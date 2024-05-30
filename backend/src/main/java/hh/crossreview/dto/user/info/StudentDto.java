package hh.crossreview.dto.user.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import hh.crossreview.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings({"unused"})
@Schema(name = "Student", requiredProperties = {"userId", "username"})
public class StudentDto {

  private Integer userId;
  private String username;

  public StudentDto(User user) {
    this.userId = user.getUserId();
    this.username = user.getUsername();
  }

  public StudentDto setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public StudentDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }
}
