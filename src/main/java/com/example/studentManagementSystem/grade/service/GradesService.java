package com.example.studentManagementSystem.grade.service;

import com.example.studentManagementSystem.grade.entity.Grade;
import com.example.studentManagementSystem.grade.repository.GradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GradesService {
 private final GradeRepository repo;

 public Mono<Grade> addOne(Grade grade){
     return repo.addGrade(grade);
 }

}
