package hh.crossreview.converter;

import hh.crossreview.dto.user.UserPatchDto;
import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class UserConverter {

  public void merge(User user, UserPatchDto userPatchDto) {
    if (userPatchDto.getUsername() != null) {
      user.setUsername(userPatchDto.getUsername());
    }
    if (userPatchDto.getName() != null) {
      user.setName(userPatchDto.getName());
    }
    if (userPatchDto.getSurname() != null) {
      user.setSurname(userPatchDto.getSurname());
    }
  }

}
