package hh.crossreview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User")
public class UserDto {

  private Integer userId;
  private String username;

  private String email;

  public UserDto(Integer userId, String username, String email) {
    this.userId = userId;
    this.username = username;
    this.email = email;
  }

  public UserDto() {
  }

  public Integer getUserId() {
    return userId;
  }

  public UserDto setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserDto setEmail(String email) {
    this.email = email;
    return this;
  }
}
