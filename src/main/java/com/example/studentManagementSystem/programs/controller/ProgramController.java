package com.example.studentManagementSystem.programs.controller;

import com.example.studentManagementSystem.programs.dto.RequestDto;
import com.example.studentManagementSystem.programs.dto.ResponseDto;
import com.example.studentManagementSystem.programs.entity.Passabilities;
import com.example.studentManagementSystem.programs.entity.Program;
import com.example.studentManagementSystem.programs.service.ProgramService;
import com.example.studentManagementSystem.semester.entity.Semester;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
@RequestMapping(value = "/program", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProgramController {
  private final ProgramService service;

  @PostMapping("/createProgram")
  public Mono<ResponseDto> addProgram(@RequestBody RequestDto program) {
    return service.addProgram(program);
  }

  @PostMapping("/addSemester/{programId}/{ects}/{passability}")
  public Mono<Program> addSemester(
      @RequestBody Semester semester,
      @PathVariable String programId,
      @PathVariable Integer ects,
      @PathVariable Integer passability) {
    return service.addSemester(semester, programId, ects, passability);
  }

  @PostMapping("/updateSemesterDate/{programId}/{semesterNumber}/{startDate}/{endDate}")
  public Mono<Program> updateSemesterDate(
      @PathVariable String programId,
      @PathVariable Integer semesterNumber,
      @PathVariable Instant startDate,
      @PathVariable Instant endDate) {
    return service.updateSemester(programId, semesterNumber, startDate, endDate);
  }

  @DeleteMapping("/deleteSemester/{programId}/{semesterId}")
  public Mono<UpdateResult> deleteSemesterFromProgramList(
      @PathVariable String programId, @PathVariable String semesterId) {
    return service.deleteSemesterFromProgramList(programId, semesterId);
  }

  @PostMapping("/getSemester/{semesterId}/{programId}")
  public Mono<Passabilities> getSemesterFromProgram(
      @PathVariable String semesterId, @PathVariable String programId) {
    return service.getSemesterFromProgram(semesterId, programId);
  }
}
