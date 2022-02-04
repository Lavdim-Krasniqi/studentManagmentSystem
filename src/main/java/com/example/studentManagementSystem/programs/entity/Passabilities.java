package com.example.studentManagementSystem.programs.entity;

import com.example.studentManagementSystem.semester.entity.Semester;
import lombok.Data;

@Data
public class Passabilities {
  public static final String SEMESTER = "semester";
  public static final String ECTS = "ects";
  public static final String SEM_PASSABILITY = "passability";
  public static final String SEMESTER_ID = "semester.id";

  private Semester semester;
  private Integer ects;
  private Integer passability;
}
