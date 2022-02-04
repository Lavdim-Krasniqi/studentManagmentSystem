package com.example.studentManagementSystem.students.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Pattern;

@Data
public class CourseDto {
  @Field(targetType = FieldType.OBJECT_ID)
  private String programId;

  private String name;
  private String semesterId;
  private Integer ects;

  private String professorPersonalNumber;

  @Pattern(regexp = "Obligated|Not Obligated")
  private String type;
}
