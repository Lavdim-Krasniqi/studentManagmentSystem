package com.example.studentManagementSystem.profesors.service;

import com.example.studentManagementSystem.profesors.entity.Professor;
import com.example.studentManagementSystem.profesors.entity.TeachesCourses;
import com.example.studentManagementSystem.profesors.repository.ProfessorRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProfessorService {

  private final ProfessorRepository repo;

  public Mono<Professor> addProfessor(Professor professor) {
    return repo.addProfessor(professor);
  }

  public Mono<UpdateResult> addTeachesCourse(
      String professorPersonalNumber, TeachesCourses teachesCourses) {
    return repo.addTeachesCourse(professorPersonalNumber, teachesCourses);
  }

  public Mono<Boolean> checkIfProfessorExists(String personalNumber) {
    return repo.checkIfProfessorExists(personalNumber);
  }
}
