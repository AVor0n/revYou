package com.example.backend.entity;

import com.example.backend.entity.enums.StudyDirection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Cohort {

  @Id
  @Column(name = "cohort_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer cohortId;

  @Column(name = "cohort_number")
  private Integer cohortNumber;

  @Column(name = "education_start_date")
  private Date educationStartDate;

  @Column(name = "education_end_date")
  private Date educationEndDate;

  @Column(name = "mm_channel_link")
  private java.lang.String mmChannelLink;

  @Column(name = "study_direction")
  @Enumerated(EnumType.STRING)
  private StudyDirection studyDirection;

  @ManyToMany(mappedBy = "cohorts")
  private List<Lecture> lectures;

  public StudyDirection getStudyDirection() {
    return studyDirection;
  }

}
