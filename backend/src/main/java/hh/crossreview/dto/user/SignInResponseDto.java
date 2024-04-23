package hh.crossreview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SignInResponse")
public class SignInResponseDto {
  private String accessToken;

  private String refreshToken;

  private String role;

  public SignInResponseDto(String accessToken, String refreshToken, String role) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.role = role;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
