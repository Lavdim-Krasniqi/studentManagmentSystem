package com.example.studentManagementSystem.university.repository;

import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.university.entity.University;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class UniversityRepository {

  private final ReactiveMongoTemplate repo;

  public Mono<University> addUniversity(University university) {
    return repo.insert(university);
  }

  public Mono<University> findUniversityByName(String name, String state, String city) {
    val c1 = Criteria.where(University.UNIVERSITY_NAME).is(name);
    val c2 = Criteria.where(University.UNIVERSITY_STATE).is(state);
    val c3 = Criteria.where(University.UNIVERSITY_CITY).is(city);

    return repo.findOne(
        Query.query(new Criteria().andOperator(c1, c2, c3)), University.class, "University");
  }

  public Mono<DeleteResult> deleteUniversity(String name, String state, String city) {
    val c1 = Criteria.where(University.UNIVERSITY_NAME).is(name);
    val c2 = Criteria.where(University.UNIVERSITY_STATE).is(state);
    val c3 = Criteria.where(University.UNIVERSITY_CITY).is(city);
    return repo.remove(Query.query(new Criteria().andOperator(c1, c2, c3)), University.class);
  }

  public Mono<Boolean> exists(String id) {
    return repo.exists(
        Query.query(Criteria.where(University.UNIVERSITY_ID).is(id)), University.class);
  }

  public Mono<University> checkForUniversity(University university) {
    val c1 = Criteria.where(University.UNIVERSITY_ID).is(university.getId());
    val c2 = Criteria.where(University.UNIVERSITY_NAME).is(university.getName());
    val c3 = Criteria.where(University.UNIVERSITY_CITY).is(university.getCity());
    val c4 = Criteria.where(University.UNIVERSITY_STATE).is(university.getState());

    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2, c3, c4)), University.class)
        .switchIfEmpty(
            Mono.error(new BadRequestException("Bad request probably university does not exist")));
  }
}
