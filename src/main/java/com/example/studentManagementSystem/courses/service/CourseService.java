package com.example.studentManagementSystem.courses.service;

import com.example.studentManagementSystem.courses.entity.Course;
import com.example.studentManagementSystem.courses.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CourseService {
  private final CourseRepository repo;

  public Mono<Course> addCourse(Course course) {
    return repo.addCourse(course);
  }

  public Mono<Course> checkForCourse(Course course) {
    return repo.checkForCourse(course);
  }
  public Mono<Course> getCourseById(String courseId){
    return repo.getCourseById(courseId);
  }
}
