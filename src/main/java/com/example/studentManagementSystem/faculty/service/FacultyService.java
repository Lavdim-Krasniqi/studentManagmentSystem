package com.example.studentManagementSystem.faculty.service;

import com.example.studentManagementSystem.faculty.entity.Faculty;
import com.example.studentManagementSystem.faculty.repository.FacultyRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FacultyService {
  private final FacultyRepository repo;

  public Mono<Faculty> addFaculty(Faculty faculty) {
    return repo.addFaculty(faculty);
  }

  public Mono<Faculty> findFacultyByName(String universityId, String name) {
    return repo.findFacultyByName(universityId, name);
  }

  public Mono<DeleteResult> deleteFaculty(String universityId, String name) {
    return repo.deleteFaculty(universityId, name);
  }

  public Mono<Boolean> exists(String id) {
    return repo.exists(id);
  }

  public Mono<Boolean> checkForUniversityId(String facultyId, String universityId) {
    return repo.checkForUniversityId(facultyId, universityId);
  }

  public Mono<Faculty> checkForFaculty(Faculty faculty) {
    return repo.checkForFaculty(faculty);
  }
}
