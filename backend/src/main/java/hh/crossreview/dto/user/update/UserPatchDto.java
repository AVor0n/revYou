package hh.crossreview.dto.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(name = "UserPatch")
public class UserPatchDto {

  @Pattern(regexp = "\\w+", message = "Field 'mmUsername' cannot be empty string")
  private String mmUsername;
  @Pattern(regexp = "[\\wа-яА-Я]+", message = "Field 'name' cannot be empty string")
  private String name;
  @Pattern(regexp = "[\\wа-яА-Я]+", message = "Field 'surname' cannot be empty string")
  private String surname;

  public String getMmUsername() {
    return mmUsername;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public void setMmUsername(String mmUsername) {
    this.mmUsername = mmUsername;
  }

  public UserPatchDto setName(String name) {
    this.name = name;
    return this;
  }

  public UserPatchDto setSurname(String surname) {
    this.surname = surname;
    return this;
  }

}
