package com.example.studentManagementSystem.deadline.service;

import com.example.studentManagementSystem.deadline.entity.Deadline;
import com.example.studentManagementSystem.deadline.repository.DeadlineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@AllArgsConstructor
public class DeadlineService {
  private final DeadlineRepository repo;

  public Mono<Deadline> addDeadline(Deadline deadline) {
    return repo.addOne(deadline);
  }

  public Mono<Boolean> checkIfSubmittedCourseIsValid(Instant time,String deadlineId) {
    return repo.checkIfSubmittedCourseIsValid(time,deadlineId);
  }
}
