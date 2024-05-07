package hh.crossreview.entity;

import hh.crossreview.entity.enums.ReviewerStatus;
import hh.crossreview.entity.interfaces.Statusable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviewers_pool")
@SuppressWarnings({"unused"})
public class Reviewer implements Statusable {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  @Enumerated(EnumType.STRING)
  private ReviewerStatus status;
  @Column(name = "appointed_at")
  private LocalDateTime appointedAt;
  @Column(name = "appointed_reviews")
  private Integer appointedReviews;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "homework_id")
  private Homework homework;

  @Override
  public ReviewerStatus getStatus() {
    return status;
  }

  public Integer getId() {
    return id;
  }

  public LocalDateTime getAppointedAt() {
    return appointedAt;
  }

  public Integer getAppointedReviews() {
    return appointedReviews;
  }

  public User getUser() {
    return user;
  }

  public Homework getHomework() {
    return homework;
  }

  public Reviewer setId(Integer id) {
    this.id = id;
    return this;
  }

  public Reviewer setStatus(ReviewerStatus status) {
    this.status = status;
    return this;
  }

  public Reviewer setAppointedAt(LocalDateTime availableAt) {
    this.appointedAt = availableAt;
    return this;
  }

  public Reviewer setAppointedReviews(Integer appointedReviews) {
    this.appointedReviews = appointedReviews;
    return this;
  }

  public Reviewer setUser(User user) {
    this.user = user;
    return this;
  }

  public Reviewer setHomework(Homework homework) {
    this.homework = homework;
    return this;
  }
}
