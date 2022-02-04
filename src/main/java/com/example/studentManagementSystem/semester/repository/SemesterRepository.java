package com.example.studentManagementSystem.semester.repository;

import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.semester.entity.Semester;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
@AllArgsConstructor
public class SemesterRepository {

  private final ReactiveMongoTemplate repo;

  public Mono<Semester> addSemester(Semester semester) {
    return checkForYearOfSemester(semester).flatMap(repo::insert);
  }

  public Mono<Boolean> checkForSemester(Semester semester) {
    val c1 = Criteria.where(Semester.SEMESTER_ID).is(semester.getId());
    val c2 = Criteria.where(Semester.SEMESTER_LEVEL).is(semester.getLevel());
    val c3 = Criteria.where(Semester.SEMESTER_END_YEAR).is(semester.getEndYear());
    val c4 = Criteria.where(Semester.SEMESTER_START_YEAR).is(semester.getStartYear());
    val c5 = Criteria.where(Semester.SEMESTER_DESCRIPTION).is(semester.getDescription());
    val c6 = Criteria.where(Semester.SEMESTER_NUMBER).is(semester.getNumber());

    return repo.exists(
        Query.query(new Criteria().andOperator(c1, c2, c3, c4, c5, c6)), Semester.class);
  }

  public Mono<Semester> checkForYearOfSemester(Semester semester) {
    return Mono.fromCallable(
            () ->
                (semester.getStartYear().compareTo(Instant.now()) > 0
                    && semester.getEndYear().compareTo(semester.getStartYear()) > 0))
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.next(semester);
              else
                sink.error(
                    new BadRequestException(
                        "Bad request semester start and end date is not valid"));
            });
  }

  public Mono<Boolean> checkForSemesterId(String semesterId) {
    return repo.exists(
        Query.query(Criteria.where(Semester.SEMESTER_ID).is(semesterId)), Semester.class);
  }
}
