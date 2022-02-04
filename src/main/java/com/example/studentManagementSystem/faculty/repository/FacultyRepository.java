package com.example.studentManagementSystem.faculty.repository;

import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.faculty.entity.Faculty;
import com.example.studentManagementSystem.university.service.UniversityService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class FacultyRepository {

  private final ReactiveMongoTemplate repo;
  private final UniversityService universityService;

  public Mono<Faculty> checkForUniversity(Faculty faculty) {
    return universityService
        .exists(faculty.getUniversityId())
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.next(faculty);
              sink.error(new BadRequestException("University does not exists"));
            });
  }

  public Mono<Faculty> addFaculty(Faculty faculty) {
    return checkForUniversity(faculty).flatMap(repo::insert);
  }

  public Mono<Faculty> findFacultyByName(String universityId, String name) {
    val c1 = Criteria.where(Faculty.FACULTY_NAME).is(name);
    val c2 = Criteria.where(Faculty.FACULTY_UNIVERSITY_ID).is(universityId);
    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2)), Faculty.class);
  }

  public Mono<DeleteResult> deleteFaculty(String universityId, String name) {
    val c1 = Criteria.where(Faculty.FACULTY_NAME).is(name);
    val c2 = Criteria.where(Faculty.FACULTY_UNIVERSITY_ID).is(universityId);
    return repo.remove(Query.query(new Criteria().andOperator(c1, c2)), Faculty.class);
  }

  public Mono<Boolean> exists(String id) {
    return repo.exists(Query.query(Criteria.where(Faculty.FACULTY_ID).is(id)), Faculty.class);
  }

  public Mono<Boolean> checkForUniversityId(String facultyId, String universityId) {
    val c1 = Criteria.where(Faculty.FACULTY_ID).is(facultyId);
    val c2 = Criteria.where(Faculty.FACULTY_UNIVERSITY_ID).is(universityId);
    return repo.exists(Query.query(new Criteria().andOperator(c1, c2)), Faculty.class);
  }

  public Mono<Faculty> checkForFaculty(Faculty faculty) {
    val c1 = Criteria.where(Faculty.FACULTY_ID).is(faculty.getId());
    val c2 = Criteria.where(Faculty.FACULTY_UNIVERSITY_ID).is(faculty.getUniversityId());
    val c3 = Criteria.where(Faculty.FACULTY_NAME).is(faculty.getName());
    val c4 = Criteria.where(Faculty.FACULTY_LOCATION).is(faculty.getLocation());

    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2, c3, c4)), Faculty.class)
        .switchIfEmpty(
            Mono.error(
                new BadRequestException(
                    "Bad request the given faculty " + "is not found in our database")));
  }
}
