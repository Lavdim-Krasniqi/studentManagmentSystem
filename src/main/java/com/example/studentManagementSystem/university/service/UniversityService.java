package com.example.studentManagementSystem.university.service;

import com.example.studentManagementSystem.university.entity.University;
import com.example.studentManagementSystem.university.repository.UniversityRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UniversityService {

  public UniversityRepository repo;

  public Mono<University> addUniversity(University university) {
    return repo.addUniversity(university);
  }

  public Mono<University> findUniversityByName(String name, String state, String city) {
    return repo.findUniversityByName(name, state, city);
  }

  public Mono<DeleteResult> deleteUniversity(String name, String state, String city) {
    return repo.deleteUniversity(name, state, city);
  }

  public Mono<Boolean> exists(String id) {
    return repo.exists(id);
  }

  public Mono<University> checkForUniversity(University university) {
    return repo.checkForUniversity(university);
  }
}
