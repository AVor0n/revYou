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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
  @NotNull(message = "'username' couldn't be empty")
  private String username;
  @Column
  private String name;

  @Column
  private String surname;

  @Column
  @NotNull(message = "'email' couldn't be empty")
  private String email;

  @Column
  @NotBlank(message = "'password' couldn't be empty")
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

  @OneToMany(mappedBy = "lector")
  private List<Lecture> lectures;

  @ManyToOne
  @JoinColumn(name = "cohort_id")
  private Cohort cohort;

  @Column(name = "cohort_id", insertable = false, updatable = false)
  private Integer cohortId;

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

  public Integer getCohortId() {
    return cohortId;
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

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setGitlabUsername(String gitlabUsername) {
    this.gitlabUsername = gitlabUsername;
  }

  public void setMmUsername(String mmUsername) {
    this.mmUsername = mmUsername;
  }

  public void setLectures(List<Lecture> lectures) {
    this.lectures = lectures;
  }

  public void setCohort(Cohort cohort) {
    this.cohort = cohort;
  }

  @Override
  public List<Cohort> getCohorts() {
    return Collections.singletonList(cohort);
  }

}
