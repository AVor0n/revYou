package hh.crossreview.dto.user.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Schema(name = "SignUpRequest")
public class SignUpRequestDto {
  @NotNull(message = "Field 'username' couldn't be empty")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String username;
  @NotNull(message = "Field 'password' couldn't be empty")
  @Size(min = 8, message = "Minimum password length - 8 characters")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;

  @NotNull(message = "Field 'confirmPassword' couldn't be empty")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String confirmPassword;

  @NotNull(message = "Field 'email' couldn't be empty")
  @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b", message = "Enter correct email")
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;


  public SignUpRequestDto(String username, String password, String confirmPassword, String email) {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
