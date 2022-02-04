package com.example.studentManagementSystem.faculty.controller;

import com.example.studentManagementSystem.faculty.entity.Faculty;
import com.example.studentManagementSystem.faculty.service.FacultyService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/faculty", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacultyController {
  private final FacultyService service;

  @PostMapping("/addFaculty")
  public Mono<Faculty> addFaculty(@RequestBody Faculty faculty) {
    return service.addFaculty(faculty);
  }

  @GetMapping("/findFaculty/{universityId}/{name}")
  public Mono<Faculty> findFacultyByName(
      @PathVariable String universityId, @PathVariable String name) {
    return service.findFacultyByName(universityId, name);
  }

  @DeleteMapping("/deleteFaculty/{universityId}/{name}")
  public Mono<DeleteResult> deleteFaculty(
      @PathVariable String universityId, @PathVariable String name) {
    return service.deleteFaculty(universityId, name);
  }
}
