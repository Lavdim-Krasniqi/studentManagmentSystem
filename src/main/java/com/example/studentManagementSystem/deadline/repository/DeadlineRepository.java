package com.example.studentManagementSystem.deadline.repository;

import com.example.studentManagementSystem.deadline.entity.Deadline;
import com.example.studentManagementSystem.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
@AllArgsConstructor
public class DeadlineRepository {
  private final ReactiveMongoTemplate repo;

  public Mono<Deadline> addOne(Deadline deadline) {
    return checkForDate(deadline).flatMap(repo::insert);
  }

  public Mono<Deadline> checkForDate(Deadline deadline) {
    return checkForIntersection(deadline.getStartDate())
        .flatMap(
            aBoolean -> {
              if (deadline.getStartDate().compareTo(Instant.now()) > 0
                  && deadline.getEndDate().compareTo(deadline.getStartDate()) > 0) {
                return Mono.fromCallable(() -> deadline);
              } else {
                return Mono.error(
                    new BadRequestException("Bad request probably the date is given bad"));
              }
            });
  }

  public Mono<Boolean> checkForIntersection(Instant startDate) {
    val c1 = Criteria.where(Deadline.END_DATE).gte(startDate);
    return repo.exists(Query.query(c1), Deadline.class)
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean)
                sink.error(new BadRequestException("this deadline has intersection with others"));
              else sink.next(true);
            });
  }

  public Mono<Boolean> checkIfSubmittedCourseIsValid(Instant time, String deadlineId) {
    val c1 =
        Criteria.where(Deadline.END_DATE)
            .gte(time)
            .and(Deadline.ID)
            .is(deadlineId)
            .and(Deadline.START_DATE)
            .lte(time);

    return repo.exists(Query.query(c1), Deadline.class);
  }
}
