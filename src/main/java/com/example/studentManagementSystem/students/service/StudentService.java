package com.example.studentManagementSystem.students.service;

import com.example.studentManagementSystem.courses.entity.Course;
import com.example.studentManagementSystem.semester.entity.Semester;
import com.example.studentManagementSystem.students.dto.*;
import com.example.studentManagementSystem.students.entity.Student;
import com.example.studentManagementSystem.students.entity.SubmittedCourse;
import com.example.studentManagementSystem.students.repository.StudentRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StudentService {
  private final StudentRepository repo;


  public Mono<ResponseDto> addStudent(StudentRequestDto studentRequestDto) {
    return repo.addStudent(studentRequestDto);
  }

  public Mono<Student> addAndUpdateSemester(
      Integer studentPersonalNumber, String programId, String semesterId, Integer studentEtcs) {
    return repo.addAndUpdateSemester(studentPersonalNumber, programId, semesterId, studentEtcs);
  }

  public Mono<UpdateResult> addCourse(Integer studentPersonalNumber, String course) {
    return repo.addCourse(studentPersonalNumber, course);
  }

  public Mono<Student> submitCourse(
      Integer studentPersonalNumber, SubmittedCourseDto submittedCourseDto) {
    SubmittedCourse submittedCourse= new SubmittedCourse();
    submittedCourse.setCourseId(submittedCourseDto.getCourseId());
    submittedCourse.setDeadlineId(submittedCourseDto.getDeadlineId());
    return repo.submitCourse(studentPersonalNumber, submittedCourse);
  }
}
