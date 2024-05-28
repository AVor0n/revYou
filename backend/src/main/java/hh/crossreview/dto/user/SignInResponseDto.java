package hh.crossreview.dto.user;

import hh.crossreview.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SignInResponse", requiredProperties = {
    "accessToken", "refreshToken", "userId",
    "username", "email"
})
@SuppressWarnings({"unused"})
public class SignInResponseDto {
  private String accessToken;
  private String refreshToken;
  private Integer userId;
  private Integer cohortId;
  private String role;
  private String username;
  private String name;
  private String surname;
  private String email;
  private String gitlabUsername;
  private String mmUsername;

  public SignInResponseDto(String accessToken, String refreshToken, String role) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.role = role;
  }

  public SignInResponseDto(String accessToken, String refreshToken, String role, User user) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.role = role;
    this.cohortId = user.getCohortId();
    this.email = user.getEmail();
    this.mmUsername = user.getMmUsername();
    this.gitlabUsername = user.getGitlabUsername();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.userId = user.getUserId();
    this.username = user.getUsername();
  }

  public String getAccessToken() {
    return accessToken;
  }

  public SignInResponseDto setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public SignInResponseDto setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  public String getRole() {
    return role;
  }

  public SignInResponseDto setRole(String role) {
    this.role = role;
    return this;
  }

  public Integer getUserId() {
    return userId;
  }

  public SignInResponseDto setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public Integer getCohortId() {
    return cohortId;
  }

  public SignInResponseDto setCohortId(Integer cohortId) {
    this.cohortId = cohortId;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public SignInResponseDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getName() {
    return name;
  }

  public SignInResponseDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getSurname() {
    return surname;
  }

  public SignInResponseDto setSurname(String surname) {
    this.surname = surname;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public SignInResponseDto setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getGitlabUsername() {
    return gitlabUsername;
  }

  public SignInResponseDto setGitlabUsername(String gitlabUsername) {
    this.gitlabUsername = gitlabUsername;
    return this;
  }

  public String getMmUsername() {
    return mmUsername;
  }

  public SignInResponseDto setMmUsername(String mmUsername) {
    this.mmUsername = mmUsername;
    return this;
  }

}
