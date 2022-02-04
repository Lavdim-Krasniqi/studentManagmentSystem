package com.example.studentManagementSystem.students.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Data
public class SubmittedCourse {
  public static final String COUNT = "count";
  @Id private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String courseId;

  private Integer grade;

  @Field(targetType = FieldType.OBJECT_ID)
  private String deadlineId;

  private boolean status;
  private Instant gradePlacedDate;
  private Instant submittedDate;
  private Integer count;
}
