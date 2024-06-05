package hh.crossreview.dto.user;

import hh.crossreview.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "UserDetailDto",
    requiredProperties = {
        "userId", "username", "name", "surname", "role"
    }
)
public class UserDetailDto {
  private Integer userId;
  private String username;
  private String name;
  private String surname;
  private UserRole role;

  public UserDetailDto() {
  }

  public Integer getUserId() {
    return userId;
  }

  public UserDetailDto setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserDetailDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserDetailDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getSurname() {
    return surname;
  }

  public UserDetailDto setSurname(String surname) {
    this.surname = surname;
    return this;
  }

  public UserRole getRole() {
    return role;
  }

  public UserDetailDto setRole(UserRole role) {
    this.role = role;
    return this;
  }
}
