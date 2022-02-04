package com.example.studentManagementSystem.students.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Pattern;
import java.time.Instant;

@Data
public class SemesterDto {
  @Field(targetType = FieldType.OBJECT_ID)
  private String id;
  private Integer number;
  @Pattern(regexp = "Bachelor|Master|Doctorate")
  private String level;
  private String description;
  private Instant startYear;
  private Instant endYear;
}
