package com.example.backend.entity;

import com.example.backend.entity.enums.UserRole;
import com.example.backend.entity.enums.UserStatus;
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
import java.util.List;

@Entity
@Table(name = "user_account")
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Column
  private String name;

  @Column
  private String surname;

  @Column
  private String email;

  @Column
  private String password;

  @Column(name = "gitlab_link")
  private String gitlabLink;

  @Column(name = "mm_link")
  private String mmLink;

  @Column
  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @Column
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @OneToMany(mappedBy = "teacher")
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

}
