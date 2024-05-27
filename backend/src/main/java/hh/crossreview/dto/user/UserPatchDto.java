package hh.crossreview.dto.user;

public class UserPatchDto {

  private String username;
  private String name;
  private String surname;

  public String getUsername() {
    return username;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public UserPatchDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserPatchDto setName(String name) {
    this.name = name;
    return this;
  }

  public UserPatchDto setSurname(String surname) {
    this.surname = surname;
    return this;
  }

}
