package hh.crossreview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SignInRequest")
public class SignInRequestDto {
  private String username;
  private String password;

  public SignInRequestDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
