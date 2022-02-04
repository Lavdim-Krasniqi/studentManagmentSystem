package com.example.studentManagementSystem.students.controller;

import com.example.studentManagementSystem.courses.entity.Course;
import com.example.studentManagementSystem.students.dto.CourseDto;
import com.example.studentManagementSystem.students.dto.StudentRequestDto;
import com.example.studentManagementSystem.students.dto.ResponseDto;
import com.example.studentManagementSystem.students.dto.SubmittedCourseDto;
import com.example.studentManagementSystem.students.entity.Student;
import com.example.studentManagementSystem.students.service.StudentService;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
  private final StudentService service;

  @PostMapping("/addOne")
  public Mono<ResponseDto> addStudent(@RequestBody StudentRequestDto student) {
    return service.addStudent(student);
  }

  @PostMapping("/semesterRegister/{studentPersonalNumber}/{programId}/{semesterId}/{studentEtcs}")
  public Mono<Student> addAndUpdateSemester(
      @PathVariable Integer studentPersonalNumber,
      @PathVariable String programId,
      @PathVariable String semesterId,
      @PathVariable Integer studentEtcs) {
    return service.addAndUpdateSemester(studentPersonalNumber, programId, semesterId, studentEtcs);
  }

  @PostMapping("/addCourse/{studentPersonalNumber}/{courseId}")
  public Mono<UpdateResult> addCourse(
      @PathVariable Integer studentPersonalNumber, @PathVariable String courseId) {
    return service.addCourse(studentPersonalNumber, courseId);
  }

  @PostMapping("/submitExam/{studentPersonalNumber}")
  public Mono<Student> submitCourse(
      @PathVariable Integer studentPersonalNumber,
      @RequestBody SubmittedCourseDto submittedCourseDto) {
    return service.submitCourse(studentPersonalNumber, submittedCourseDto);
  }
}
