package hh.crossreview.dto.homework;

public class HomeworkAuthorDto {
  private Integer id;
  private String firstName;
  private String lastName;

  public HomeworkAuthorDto(Integer id, String firstName, String lastName) {
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
