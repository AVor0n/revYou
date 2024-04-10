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

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
