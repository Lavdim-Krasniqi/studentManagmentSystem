package com.example.studentManagementSystem.students.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
public class StudentRequestDto {
  private String name;
  private String surname;
  private Integer personalNumber;

  private Date birthdayDate;

  @Pattern(regexp = "Male|Female", message = "Gender should be Male or Female")
  private String gender;

  @Email @NotNull private String email;
  private String password;
  private String phoneNumber;
  private String universityId;
  private String facultyId;
  private String departmentId;
  private String programId;
  private Integer totalEtcs;
  private List<StudentSemesters> semesterList;
  private List<CourseDto> listOfCourses;
  private List<SubmittedCourseDto> submittedCoursesList;
}
