package com.example.studentManagementSystem.semester.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import java.time.Instant;

@Document("Semester")
@Data
 @CompoundIndex(def = "{'number':1}", name = "semester_number", unique = true)
public class Semester {

  public static final String SEMESTER_ID = "id";
  public static final String SEMESTER_LEVEL = "level";
  public static final String SEMESTER_START_YEAR = "startYear";
  public static final String SEMESTER_END_YEAR = "endYear";
  public static final String SEMESTER_DESCRIPTION = "description";
  public static final String SEMESTER_NUMBER = "number";

  @Id private String id;
  private Integer number;
  @Pattern(regexp = "Bachelor|Master|Doctorate")
  private String level;
  private String description;
  private Instant startYear;
  private Instant endYear;
}
