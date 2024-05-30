package hh.crossreview.dto.user.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "SignInRequest")
public class SignInRequestDto {
  @NotNull(message = "Field 'username' couldn't be empty")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String username;
  @NotNull(message = "Field 'password' couldn't be empty")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
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
