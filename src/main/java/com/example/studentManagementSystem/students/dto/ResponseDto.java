package com.example.studentManagementSystem.students.dto;

import com.example.studentManagementSystem.departments.entity.Department;
import com.example.studentManagementSystem.faculty.entity.Faculty;
import com.example.studentManagementSystem.programs.entity.Program;
import com.example.studentManagementSystem.university.entity.University;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;

@Data
@Builder
public class ResponseDto {
  private String name;
  private String surname;
  private Integer personalNumber;
  private Date birthdayDate;
  private String gender;
  private String email;
  private String password;
  private String phoneNumber;
  private String university;
  private String faculty;
  private String department;
  private String program;
  private Integer totalEtcs;

}
