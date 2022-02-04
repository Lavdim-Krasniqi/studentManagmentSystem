package com.example.studentManagementSystem.grade.repository;

import com.example.studentManagementSystem.courses.service.CourseService;
import com.example.studentManagementSystem.grade.entity.Grade;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class GradeRepository {
  private final ReactiveMongoTemplate repo;
  private final CourseService courseService;

  public Mono<Grade> addGrade(Grade grade) {
    return courseService.checkForCourse(grade.getCourse()).flatMap(course -> repo.insert(grade));
  }
}
