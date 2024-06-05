package hh.crossreview.dto.user.info;

import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserInfo", requiredProperties = {
    "userId", "role", "username", "email"
})
public class UserInfoDto {
  
  private Integer userId;
  private Integer cohortId;
  private UserRole role;
  private String username;
  private String name;
  private String surname;
  private String email;
  private String gitlabUsername;
  private String mmUsername;

  public UserInfoDto(User user) {
    this.role = user.getRole();
    this.cohortId = user.getCohortId();
    this.email = user.getEmail();
    this.mmUsername = user.getMmUsername();
    this.gitlabUsername = user.getGitlabUsername();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.userId = user.getUserId();
    this.username = user.getUsername();
  }

  public UserRole getRole() {
    return role;
  }

  public UserInfoDto setRole(UserRole role) {
    this.role = role;
    return this;
  }

  public Integer getUserId() {
    return userId;
  }

  public UserInfoDto setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public Integer getCohortId() {
    return cohortId;
  }

  public UserInfoDto setCohortId(Integer cohortId) {
    this.cohortId = cohortId;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserInfoDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserInfoDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getSurname() {
    return surname;
  }

  public UserInfoDto setSurname(String surname) {
    this.surname = surname;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserInfoDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getGitlabUsername() {
    return gitlabUsername;
  }

  public UserInfoDto setGitlabUsername(String gitlabUsername) {
    this.gitlabUsername = gitlabUsername;
    return this;
  }

  public String getMmUsername() {
    return mmUsername;
  }

  public UserInfoDto setMmUsername(String mmUsername) {
    this.mmUsername = mmUsername;
    return this;
  }
  
}
