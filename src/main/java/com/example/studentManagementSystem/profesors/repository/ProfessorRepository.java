package com.example.studentManagementSystem.profesors.repository;

import com.example.studentManagementSystem.courses.service.CourseService;
import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.profesors.entity.Professor;
import com.example.studentManagementSystem.profesors.entity.TeachesCourses;
import com.example.studentManagementSystem.programs.service.ProgramService;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Repository
@AllArgsConstructor
public class ProfessorRepository {

  private final ReactiveMongoTemplate repo;
  private final ProgramService programService;
  private final CourseService courseService;

  public Mono<Professor> addProfessor(Professor professor) {
    professor.setTeachesCourses(new ArrayList<>());
    return checkForPersonalNumber(professor.getPersonalNumber())
        .flatMap(professor1 -> repo.insert(professor));
  }
  public Mono<UpdateResult> addTeachesCourse(
      String professorPersonalNumber, TeachesCourses teachesCourses) {
    val query =
        new Query(Criteria.where(Professor.PROFESSOR_PERSONAL_NUMBER).is(professorPersonalNumber));
    Update update = new Update();
    update.addToSet("TeachesCourses", teachesCourses);
    return checkForProfessorDetails(teachesCourses)
        .flatMap(it -> repo.updateFirst(query, update, Professor.class));
  }

  public Mono<TeachesCourses> checkForProfessorDetails(TeachesCourses teachesCourses) {
    return programService
        .checkIfGivenEntityMatchesProgram(teachesCourses)
        .flatMap(program -> courseService.checkForCourse(teachesCourses.getCourse()))
        .map(course -> teachesCourses);
  }

  public Mono<String> checkForPersonalNumber(String professorPersonalNumber) {
    return repo.exists(
            Query.query(
                Criteria.where(Professor.PROFESSOR_PERSONAL_NUMBER).is(professorPersonalNumber)),
            Professor.class)
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean)
                sink.error(
                    new BadRequestException("This professor actually exists in our database"));
              else sink.next(professorPersonalNumber);
            });
  }

  public Mono<Boolean> checkIfProfessorExists(String personalNumber) {
    return repo.exists(
        Query.query(Criteria.where(Professor.PROFESSOR_PERSONAL_NUMBER).is(personalNumber)),
        Professor.class);
  }
}
