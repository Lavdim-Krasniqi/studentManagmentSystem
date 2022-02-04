package com.example.studentManagementSystem.deadline.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document("DeadLine")
@Data
public class Deadline {

  public static final String START_DATE = "startDate";
  public static final String END_DATE = "endDate";
  public static final String ID = "id";

  @Id private String id;
  private String name;
  private Instant startDate;
  private Instant endDate;
  private String level;
  private String type;
}
