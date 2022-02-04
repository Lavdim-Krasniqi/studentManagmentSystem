package com.example.studentManagementSystem.profesors.controller;

import com.example.studentManagementSystem.profesors.entity.Professor;
import com.example.studentManagementSystem.profesors.entity.TeachesCourses;
import com.example.studentManagementSystem.profesors.service.ProfessorService;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/professor", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfessorController {

  private final ProfessorService service;

  @PostMapping("/addOne")
  public Mono<Professor> addProfessor(@RequestBody Professor professor) {
    return service.addProfessor(professor);
  }

  @PostMapping("/addCourse/{professorPersonalNumber}")
  public Mono<UpdateResult> addTeachesCourse(
      @PathVariable String professorPersonalNumber, @RequestBody TeachesCourses teachesCourses) {
    return service.addTeachesCourse(professorPersonalNumber, teachesCourses);
  }
}
