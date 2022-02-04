package com.example.studentManagementSystem.programs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

@Document("Program")
@Data
@CompoundIndex(def = "{'departmentId':1, 'name':1}", name = "program_index", unique = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Program {

  public static final String ID = "id";
  public static final String UNIVERSITY_ID = "universityId";
  public static final String FACULTY_ID = "facultyId";
  public static final String DEPARTMENT_ID = "departmentId";
  public static final String NAME = "name";
  public static final String LEVEL = "level";
  public static final String SEM_NUMBER = "semesterNumber";
  public static final String PASSABILIES = "passabilies";
  public static final String PASSABILIES_SEMESTER = "passabilies.semester";
  public static final String PASSABILITIES_SEM_ID = "passabilies.semester.id";
  public static final String PASSABILITY_SEM_NUM = "passabilies.semester.number";
  public static final String PASSABILITY_SEM_START_DATE = "passabilies.$.semester.startYear";
  public static final String PASSABILITY_SEM_END_DATE = "passabilies.$.semester.endYear";
  public static final String SEMESTER_UPDATE = "passabilies.$.semester";
  public static final String SEMESTER_ECTS = "passabilies.ects";
  public static final String SEMESTER_PASSABILITY = "passabilies.passability";
  public static final String PASSABILITY_SEM_NUMBER="semester.number";
  public static final String PASSABILITY_SEM_START="semester.startYear";
  public static final String PASSABILITY_SEM_END="semester.endYear";
  public static final String PASSABILITY_SEM_LEVEL= "semester.level";
  public static final String SEM_ID= "semester.id";
  public static final String SEMESTER= "semester";


  @Id private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String universityId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String facultyId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String departmentId;

  private String name;
  private String level;
  private Integer semesterNumber;
  private List<Passabilities> passabilies;
}
