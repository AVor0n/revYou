package hh.crossreview.dto.homework;

public class GetHomeworkAuthorDto {
  private Integer id;
  private String firstName;
  private String lastName;

  public GetHomeworkAuthorDto(Integer id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
