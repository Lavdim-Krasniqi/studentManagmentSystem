package com.example.studentManagementSystem.departments.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Document("Department")
@CompoundIndex(def = "{'facultyId':1, 'name':1}", unique = true, name = "department_index")
@Builder
public class Department {
  public static final String DEPARTMENT_ID = "id";
  public static final String DEPARTMENT_UNIVERSITY_ID = "universityId";
  public static final String DEPARTMENT_FACULTY_ID = "facultyId";
  public static final String DEPARTMENT_NAME = "name";

  @Id private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String universityId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String facultyId;

  private String name;
}
