package hh.crossreview.dto.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "PasswordPatch", requiredProperties = {"password", "confirmationPassword"})
public class PasswordPatchDto {

  @NotNull(message = "Field 'password' couldn't be empty")
  @Size(min = 8, message = "Minimum password length - 8 characters")
  private String password;
  @NotNull(message = "Field 'confirmPassword' couldn't be empty")
  private String confirmationPassword;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmationPassword() {
    return confirmationPassword;
  }

  public void setConfirmationPassword(String confirmationPassword) {
    this.confirmationPassword = confirmationPassword;
  }

}
