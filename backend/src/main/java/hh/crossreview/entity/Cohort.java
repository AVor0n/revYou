package hh.crossreview.entity;

import hh.crossreview.entity.enums.StudyDirection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@SuppressWarnings({"unused"})
public class Cohort {

  @Id
  @Column(name = "cohort_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer cohortId;

  @Column(name = "cohort_number")
  private Integer cohortNumber;

  @Column(name = "education_start_date")
  private LocalDate educationStartDate;

  @Column(name = "education_end_date")
  private LocalDate educationEndDate;

  @Column(name = "mm_channel_link")
  private java.lang.String mmChannelLink;

  @Column(name = "study_direction")
  @Enumerated(EnumType.STRING)
  private StudyDirection studyDirection;

  @ManyToMany(mappedBy = "cohorts")
  private List<Lecture> lectures;

  public Integer getCohortId() {
    return cohortId;
  }

  public Integer getCohortNumber() {
    return cohortNumber;
  }

  public LocalDate getEducationStartDate() {
    return educationStartDate;
  }

  public LocalDate getEducationEndDate() {
    return educationEndDate;
  }

  public String getMmChannelLink() {
    return mmChannelLink;
  }

  public StudyDirection getStudyDirection() {
    return studyDirection;
  }

  public List<Lecture> getLectures() {
    return lectures;
  }

  public Cohort setCohortId(Integer cohortId) {
    this.cohortId = cohortId;
    return this;
  }

  public Cohort setCohortNumber(Integer cohortNumber) {
    this.cohortNumber = cohortNumber;
    return this;
  }

  public Cohort setEducationStartDate(LocalDate educationStartDate) {
    this.educationStartDate = educationStartDate;
    return this;
  }

  public Cohort setEducationEndDate(LocalDate educationEndDate) {
    this.educationEndDate = educationEndDate;
    return this;
  }

  public Cohort setMmChannelLink(String mmChannelLink) {
    this.mmChannelLink = mmChannelLink;
    return this;
  }

  public Cohort setStudyDirection(StudyDirection studyDirection) {
    this.studyDirection = studyDirection;
    return this;
  }

  public Cohort setLectures(List<Lecture> lectures) {
    this.lectures = lectures;
    return this;
  }
}
