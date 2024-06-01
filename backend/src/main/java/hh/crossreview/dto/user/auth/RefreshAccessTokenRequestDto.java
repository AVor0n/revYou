package hh.crossreview.dto.user.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "RefreshAccessTokenRequestDto")
public class RefreshAccessTokenRequestDto {

  @NotNull(message = "Field 'refreshToken' couldn't be empty")
  private String refreshToken;


  public RefreshAccessTokenRequestDto(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public RefreshAccessTokenRequestDto() {
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
