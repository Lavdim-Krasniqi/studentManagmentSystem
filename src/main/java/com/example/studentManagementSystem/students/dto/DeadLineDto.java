package com.example.studentManagementSystem.students.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class DeadLineDto {
  private String name;
  private Instant startDate;
  private Instant endDate;
  private String level;
  private String type;
}
