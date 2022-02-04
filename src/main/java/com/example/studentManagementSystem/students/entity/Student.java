package com.example.studentManagementSystem.students.entity;

import com.example.studentManagementSystem.students.dto.CourseDto;
import com.example.studentManagementSystem.students.dto.StudentSemesters;
import com.example.studentManagementSystem.students.dto.SubmittedCourseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Document("Students")
@AllArgsConstructor
@Data
@CompoundIndex(def = "{'email':1, 'password':1}", name = "email-password", unique = true)
@Builder
public class Student {
  public static final String PERSONAL_NUMBER = "personalNumber";
  public static final String LIST_OF_SEMESTERS = "semesterList";
  public static final String ID = "id";
  public static final String SEMESTER_NUMBER = "semesterList.semester.number";
  public static final String SEMESTER_ID = "semesterList.semester.id";
  public static final String UPDATE_SEMESTER = "semesterList.$";
  public static final String UPDATE_SEMESTER_START_DATE = "semesterList.$[].semester.startYear";
  public static final String UPDATE_SEMESTER_END_DATE = "semesterList.$[].semester.endYear";
  public static final String SEMESTER_COUNT = "semesterList.count";
  public static final String SEMESTER = "semesterList.semester";
  public static final String PROGRAM_ID = "programId";
  public static final String LIST_OF_COURSES = "listOfCourses";
  public static final String LIST_OF_COURSES_ID = "listOfCourses._id";
  public static final String SUBMITTED_COURSE_LIST_DEADLINE_ID = "submittedCoursesList.deadlineId";
  public static final String SUBMITTED_COURSE_LIST = "submittedCoursesList";
  public static final String SUBMITTED_COURSE_LIST_COURSE_ID = "submittedCoursesList.courseId";
  public static final String UPDATE_DEADLINE_ID = "submittedCoursesList.$.deadlineId";
  public static final String DEADLINE_ID = "deadlineId";
  public static final String COURSE_ID = "courseId";

  @Id private String id;

  private String name;
  private String surname;

  @Indexed(unique = true, name = "StudentPersonalNumber")
  private Integer personalNumber;

  private Date birthdayDate;

  @Pattern(regexp = "Male|Female", message = "Gender should be Male or Female")
  private String gender;

  @Email @NotNull private String email;
  private String password;
  private String phoneNumber;

  @Field(targetType = FieldType.OBJECT_ID)
  private String universityId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String facultyId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String departmentId;

  @Field(targetType = FieldType.OBJECT_ID)
  private String programId;

  private List<StudentSemesters> semesterList;
  private List<CourseDto> listOfCourses;

  private Double average;
  private Integer totalEcts;
  private List<SubmittedCourseDto> submittedCoursesList;
}
