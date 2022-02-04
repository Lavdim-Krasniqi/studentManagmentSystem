package com.example.studentManagementSystem.grade.controller;

import com.example.studentManagementSystem.grade.entity.Grade;
import com.example.studentManagementSystem.grade.service.GradesService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/grades", produces = MediaType.APPLICATION_JSON_VALUE)
public class GradeController {
  private final GradesService service;

  @PostMapping("/addOne")
  public Mono<Grade> addOne(@RequestBody Grade grade) {
    return service.addOne(grade);
  }
}
