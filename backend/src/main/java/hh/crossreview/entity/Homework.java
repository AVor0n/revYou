package hh.crossreview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Homework {

  @Id
  @Column(name = "homework_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer homeworkId;

  @Column(name = "homework_link")
  private String homeworkLink;

  @Column
  private String title;

  @Column
  private String theme;

  @Column
  private String description;

  @Column(name = "creation_timestamp")
  private Date creationTimestamp;

  @Column(name = "completion_deadline")
  private Date completionDeadline;

  @Column(name = "review_deadline")
  private Date reviewDeadline;

  @ManyToOne
  @JoinColumn(name = "lecture_id")
  private Lecture lecture;

  public Lecture getLecture() {
    return lecture;
  }

}
