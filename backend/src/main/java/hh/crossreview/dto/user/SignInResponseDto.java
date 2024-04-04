package hh.crossreview.dto.user;

public class SignInResponseDto {
  private String accessToken;

  public SignInResponseDto(String token) {
    this.accessToken = token;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
