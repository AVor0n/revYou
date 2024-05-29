package hh.crossreview.converter;

import hh.crossreview.dto.user.UserDetailDto;
import hh.crossreview.dto.user.UserDetailWrapperDto;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class UserDetailConverter {
  public UserDetailDto convertToUserDetailDto(User user) {
    return new UserDetailDto()
            .setUserId(user.getUserId())
            .setUsername(user.getUsername())
            .setSurname(user.getSurname())
            .setName(user.getName())
            .setRole(user.getRole());
  }

  public UserDetailWrapperDto convertToUserDetailWrapperDto(List<User> users) {
    List<UserDetailDto> userDetailsDtos = users
            .stream()
            .map(this::convertToUserDetailDto)
            .toList();
    return new UserDetailWrapperDto(userDetailsDtos);
  }
}
