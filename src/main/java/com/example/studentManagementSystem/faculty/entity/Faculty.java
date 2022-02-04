package com.example.studentManagementSystem.faculty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Document("Faculty")
@AllArgsConstructor
@CompoundIndex(def = "{'universityId':1, 'name':1}", name = "index_of_faculty", unique = true)
public class Faculty {

  public static final String FACULTY_ID = "id";
  public static final String FACULTY_UNIVERSITY_ID = "universityId";
  public static final String FACULTY_NAME = "name";
  public static final String FACULTY_LOCATION = "location";

  @Id private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String universityId;

  private String name;
  private String location;
}
