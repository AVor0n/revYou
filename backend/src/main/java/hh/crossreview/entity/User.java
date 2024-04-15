package hh.crossreview.entity;

import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.entity.enums.UserStatus;
import hh.crossreview.entity.interfaces.Cohortable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user_account")
@SuppressWarnings({"unused"})
public class User implements Cohortable {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Column
  private String username;
  @Column
  private String name;

  @Column
  private String surname;

  @Column
  private String email;

  @Column
  private String password;

  @Column(name = "gitlab_username")
  private String gitlabUsername;

  @Column(name = "mm_username")
  private String mmUsername;

  @Column
  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @Column
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @OneToMany(mappedBy = "author")
  private List<Lecture> lectures;

  @ManyToOne
  @JoinColumn(name = "cohort_id")
  private Cohort cohort;

  public Integer getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public UserRole getRole() {
    return role;
  }

  public String getEmail() {
    return email;
  }

  public Cohort getCohort() {
    return cohort;
  }

  public String getGitlabUsername() {
    return gitlabUsername;
  }

  public String getMmUsername() {
    return mmUsername;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  @Override
  public List<Cohort> getCohorts() {
    return Collections.singletonList(cohort);
  }

}
