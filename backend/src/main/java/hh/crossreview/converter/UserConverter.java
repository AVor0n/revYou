package hh.crossreview.converter;

import hh.crossreview.dto.user.update.UserPatchDto;
import hh.crossreview.entity.User;
import hh.crossreview.utils.NullUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class UserConverter {

  public void merge(User user, UserPatchDto userPatchDto) {
    NullUtils.updateIfPresent(user::setMmUsername, userPatchDto.getMmUsername());
    NullUtils.updateIfPresent(user::setName, userPatchDto.getName());
    NullUtils.updateIfPresent(user::setSurname, userPatchDto.getSurname());
  }

}
