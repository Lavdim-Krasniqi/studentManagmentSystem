package com.example.studentManagementSystem.profesors.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Document("Professor")
@Data
@CompoundIndex(def = "{'personalNumber':1}", name = "personalNumber", unique = true)
public class Professor {

  public static final String PROFESSOR_ID = "id";
  public static final String PROFESSOR_NAME = "name";
  public static final String PROFESSOR_SURNAME = "surname";
  public static final String PROFESSOR_PERSONAL_NUMBER = "personalNumber";

  @Id private String id;
  private String name;
  private String surname;
  private String personalNumber;
  private List<TeachesCourses> teachesCourses;
  @Email private String email;
  @Pattern(regexp = "Male|Female")
  private String gender;
  private Date birthday;
}
