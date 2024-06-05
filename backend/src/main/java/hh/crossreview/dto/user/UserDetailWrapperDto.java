package hh.crossreview.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "UserWrapper", requiredProperties = {"data"})
public class UserDetailWrapperDto {
  private List<UserDetailDto> data;

  public UserDetailWrapperDto(List<UserDetailDto> data) {
    this.data = data;
  }

  public List<UserDetailDto> getData() {
    return data;
  }

  public UserDetailWrapperDto setData(List<UserDetailDto> data) {
    this.data = data;
    return this;
  }
}
