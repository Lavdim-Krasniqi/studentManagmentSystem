package com.example.studentManagementSystem.courses.controller;

import com.example.studentManagementSystem.courses.entity.Course;
import com.example.studentManagementSystem.courses.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {
  private final CourseService service;

  @PostMapping("/addOne")
  public Mono<Course> addCourse(@RequestBody Course course) {
    return service.addCourse(course);
  }
}
