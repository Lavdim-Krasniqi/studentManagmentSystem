package com.example.studentManagementSystem.semester.controller;

import com.example.studentManagementSystem.semester.entity.Semester;
import com.example.studentManagementSystem.semester.service.SemesterService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/semester", produces = MediaType.APPLICATION_JSON_VALUE)
public class SemesterController {
  private final SemesterService service;

  @PostMapping("/addOne")
  public Mono<Semester> addSemester(@RequestBody Semester semester) {
    return service.addSemester(semester);
  }
}
