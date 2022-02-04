package com.example.studentManagementSystem.semester.service;

import com.example.studentManagementSystem.semester.entity.Semester;
import com.example.studentManagementSystem.semester.repository.SemesterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SemesterService {
  private final SemesterRepository repo;

  public Mono<Semester> addSemester(Semester semester) {
    return repo.addSemester(semester);
  }

  public Mono<Boolean> checkForSemester(Semester semester) {
    return repo.checkForSemester(semester);
  }
  public Mono<Boolean> checkForSemesterId(String semesterId){
    return repo.checkForSemesterId(semesterId);
  }
}
