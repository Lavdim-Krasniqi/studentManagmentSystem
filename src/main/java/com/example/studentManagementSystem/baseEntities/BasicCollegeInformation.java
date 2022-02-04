package com.example.studentManagementSystem.baseEntities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
public class BasicCollegeInformation {
  @Field(targetType = FieldType.OBJECT_ID)
  private String universityId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String facultyId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String departmentId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String programId;
}
