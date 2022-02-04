package com.example.studentManagementSystem.courses.repository;

import com.example.studentManagementSystem.courses.entity.Course;
import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.profesors.service.ProfessorService;
import com.example.studentManagementSystem.programs.service.ProgramService;
import com.example.studentManagementSystem.semester.service.SemesterService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CourseRepository {
  @Autowired private ReactiveMongoTemplate repo;
  @Autowired private SemesterService semesterService;
  @Autowired private ProgramService programService;
  @Autowired @Lazy private ProfessorService professorService;

  // qeta duhet me e ban qe me e kontrollu edhe profosor id
  public Mono<Course> addCourse(Course course) {
    return checkForValidSemester(course)
        .flatMap(this::checkForProgramId)
        .flatMap(this::checkForDuplicateCourse)
        .flatMap(s -> professorService.checkIfProfessorExists(course.getProfessorPersonalNumber()))
        .flatMap(
            aBoolean -> {
              if (aBoolean) return repo.insert(course);
              else
                return Mono.error(
                    new BadRequestException("Professor personal number does not exists"));
            });
  }

  public Mono<Course> checkForValidSemester(Course course) {
    return semesterService
        .checkForSemesterId(course.getSemesterId())
        .handle(
            (exists, sink) -> {
              if (exists) sink.next(course);
              else
                sink.error(
                    new BadRequestException(
                        "Probably the semester is not " + "found in our database"));
            });
  }

  public Mono<Course> checkForProgramId(Course course) {
    return programService
        .exists(course.getProgramId())
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.next(course);
              else sink.error(new BadRequestException("Probably the ProgramId is given bad"));
            });
  }

  public Mono<Course> checkForDuplicateCourse(Course course) {
    val c1 =
        Criteria.where(Course.PROGRAM_ID)
            .is(course.getProgramId())
            .and(Course.SEMESTER_ID)
            .is(course.getSemesterId())
            .and(Course.PROFESSOR_PERSONAL_NUMBER)
            .is(course.getProfessorPersonalNumber())
            .and(Course.NAME)
            .is(course.getName())
            .and(Course.TYPE)
            .is(course.getType());
    return repo.exists(Query.query(c1), Course.class)
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.error(new BadRequestException("This course already exists"));
              else sink.next(course);
            });
  }

  public Mono<Course> checkForCourse(Course course) {
    val c1 = Criteria.where(Course.ID).is(course.getId());
    val c2 =
        Criteria.where(Course.PROFESSOR_PERSONAL_NUMBER).is(course.getProfessorPersonalNumber());
    val c3 = Criteria.where(Course.PROGRAM_ID).is(course.getProgramId());
    val c4 = Criteria.where(Course.SEMESTER_ID).is(course.getSemesterId());
    val c5 = Criteria.where(Course.TYPE).is(course.getType());

    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2, c3, c4, c5)), Course.class)
        .switchIfEmpty(
            Mono.error(
                new BadRequestException(
                    "Bad request the given course " + "is not found in our database")));
  }

  public Mono<Course> getCourseById(String courseId){
      return repo.findOne(Query.query(Criteria.where(Course.ID).is(courseId)),Course.class)
              .switchIfEmpty(Mono.error(new BadRequestException("This course does not exists")));
  }
}
