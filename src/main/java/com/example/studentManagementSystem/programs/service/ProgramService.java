package com.example.studentManagementSystem.programs.service;

import com.example.studentManagementSystem.baseEntities.BasicCollegeInformation;
import com.example.studentManagementSystem.programs.dto.RequestDto;
import com.example.studentManagementSystem.programs.dto.ResponseDto;
import com.example.studentManagementSystem.programs.entity.Passabilities;
import com.example.studentManagementSystem.programs.entity.Program;
import com.example.studentManagementSystem.programs.repository.ProgramRepository;
import com.example.studentManagementSystem.semester.entity.Semester;
import com.example.studentManagementSystem.students.dto.SemesterDto;
import com.example.studentManagementSystem.students.dto.StudentRequestDto;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@AllArgsConstructor
public class ProgramService {

  private final ProgramRepository repo;

  public Mono<ResponseDto> addProgram(RequestDto requestDto) {
    return repo.addProgram(requestDto);
  }

  public Mono<Boolean> exists(String id) {
    return repo.exists(id);
  }

  public Mono<Program> checkForProgram(Program program) {
    return repo.checkForProgram(program);
  }

  public Mono<Program> updateSemester(
      String programId, Integer numberOfSemester, Instant startYear, Instant endYear) {
    return repo.updateSemesterDate(programId, numberOfSemester, startYear, endYear);
  }

  public Mono<Program> addSemester(
      Semester semester, String programId, Integer ects, Integer passability) {
    return repo.addSemester(semester, programId, ects, passability);
  }

  public Mono<UpdateResult> deleteSemesterFromProgramList(String programId, String semesterId) {
    return repo.deleteSemesterFromProgramList(programId, semesterId);
  }

  public <T extends BasicCollegeInformation> Mono<Program> checkIfGivenEntityMatchesProgram(
      T information) {
    return repo.checkIfGivenEntityMatchesProgram(information);
  }

  public Mono<Program> checkForStudentProgram(StudentRequestDto student) {
    return repo.checkForStudentProgram(student);
  }

  public Mono<Passabilities> getSemesterFromProgram(String semesterId, String programId) {
    return repo.getSemesterFromProgram(semesterId, programId);
  }
}
