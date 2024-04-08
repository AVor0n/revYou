package hh.crossreview.entity;

import hh.crossreview.entity.interfaces.Authorable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Lecture implements Authorable {

  @Id
  @Column(name = "lecture_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer lectureId;

  @Column
  private String name;

  @Column(name = "lecture_date")
  private Date lectureDate;

  @Column(name = "zoom_link")
  private String zoomLink;

  @Column(name = "presentation_link")
  private String presentationLink;

  @NotNull(message = "Field 'teacherId' couldn't be empty")
  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @OneToMany(mappedBy = "lecture")
  private List<Homework> homeworks;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "lecture_cohort",
      joinColumns = { @JoinColumn(name = "lecture_id") },
      inverseJoinColumns = { @JoinColumn(name = "cohort_id") }
  )
  private List<Cohort> cohorts;

  public Integer getLectureId() {
    return lectureId;
  }

  public String getName() {
    return name;
  }

  public User getAuthor() {
    return author;
  }

  public List<Cohort> getCohorts() {
    return cohorts;
  }

  @Override
  public Integer getAuthorId() {
    return author.getUserId();
  }
}
