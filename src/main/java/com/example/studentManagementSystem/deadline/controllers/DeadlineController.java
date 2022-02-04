package com.example.studentManagementSystem.deadline.controllers;

import com.example.studentManagementSystem.deadline.entity.Deadline;
import com.example.studentManagementSystem.deadline.service.DeadlineService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/deadline", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeadlineController {
  private final DeadlineService service;

  @PostMapping("/addOne")
  public Mono<Deadline> addOne(@RequestBody Deadline deadline) {
    return service.addDeadline(deadline);
  }
}
