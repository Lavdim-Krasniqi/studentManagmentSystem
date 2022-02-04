package com.example.studentManagementSystem.university.controller;

import com.example.studentManagementSystem.university.entity.University;
import com.example.studentManagementSystem.university.service.UniversityService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/university", produces = MediaType.APPLICATION_JSON_VALUE)
public class UniversityController {

  private final UniversityService service;

  @PostMapping("/addOne")
  public Mono<University> addUniversity(@RequestBody @Valid University university) {
    return service.addUniversity(university);
  }

  @GetMapping("/findOne/{name}/{state}/{city}")
  public Mono<University> findUniversityByName(
      @PathVariable String name, @PathVariable String state, @PathVariable String city) {
    return service.findUniversityByName(name, state, city);
  }

  @DeleteMapping("/deleteOne/{name}/{state}/{city}")
  public Mono<DeleteResult> deleteUniversity(
      @PathVariable String name, @PathVariable String state, @PathVariable String city) {
    return service.deleteUniversity(name, state, city);
  }
}
