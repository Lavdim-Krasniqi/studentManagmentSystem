package com.example.studentManagementSystem.courses.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Pattern;

@Document("Course")
@Data
public class Course {

  public static final String ID = "id";
  public static final String PROGRAM_ID = "programId";
  public static final String PROFESSOR_PERSONAL_NUMBER = "professorPersonalNumber";
  public static final String SEMESTER_ID = "semesterId";
  public static final String TYPE = "type";
  public static final String NAME = "name";

  @Id private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String programId;

  private String name;
  @Field(targetType = FieldType.OBJECT_ID)
  private String semesterId;
  private Integer ects;

  private String professorPersonalNumber;

  @Pattern(regexp = "Obligated|Not Obligated")
  private String type;
}
