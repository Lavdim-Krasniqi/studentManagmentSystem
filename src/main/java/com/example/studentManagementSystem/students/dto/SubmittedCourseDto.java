package com.example.studentManagementSystem.students.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Data
public class SubmittedCourseDto {
  @Field(targetType = FieldType.OBJECT_ID)
  private String courseId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String deadlineId;
}
