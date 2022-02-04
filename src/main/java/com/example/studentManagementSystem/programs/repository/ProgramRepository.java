package com.example.studentManagementSystem.programs.repository;

import com.example.studentManagementSystem.baseEntities.BasicCollegeInformation;
import com.example.studentManagementSystem.departments.service.DepartmentService;
import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.programs.dto.RequestDto;
import com.example.studentManagementSystem.programs.dto.ResponseDto;
import com.example.studentManagementSystem.programs.entity.Passabilities;
import com.example.studentManagementSystem.programs.entity.Program;
import com.example.studentManagementSystem.semester.entity.Semester;
import com.example.studentManagementSystem.semester.service.SemesterService;
import com.example.studentManagementSystem.students.dto.StudentRequestDto;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProgramRepository {

  private final ReactiveMongoTemplate repo;
  private final DepartmentService departmentService;
  private final SemesterService semesterService;

  public Mono<ResponseDto> addProgram(RequestDto program) {
    if (program.getPassabilities() == null) {
      List<Passabilities> passability = new ArrayList<Passabilities>();
      program.setPassabilities(passability);
    }
    return getProgram(program)
        .flatMap(repo::insert)
        .map(
            program1 ->
                ResponseDto.builder()
                    .id(program1.getId())
                    .universityId(program1.getUniversityId())
                    .facultyId(program1.getFacultyId())
                    .departmentId(program1.getDepartmentId())
                    .name(program1.getName())
                    .level(program1.getLevel())
                    .semesterNumber(program1.getSemesterNumber())
                    .passabilies(program1.getPassabilies())
                    .build());
  }

  public Mono<Program> addSemester(
      Semester semester, String program_id, Integer ects, Integer passability) {
    val c1 = where(Program.ID).is(program_id);
    Update update = new Update();
    update.push(
        Program.PASSABILIES,
        new BasicDBObject("semester", semester)
            .append("ects", ects)
            .append("passability", passability));
    return checkForSemesterNumber(semester)
        .flatMap(semesterService::checkForSemester)
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.next(semester);
              else
                sink.error(
                    new BadRequestException("This semester does not exists in semesters database"));
            })
        .flatMap(semester1 -> repo.findAndModify(Query.query(c1), update, Program.class));
  }

  public Mono<Program> updateSemesterDate(
      String programId, Integer semesterNumber, Instant startYear, Instant endYear) {
    val c2 = where(Program.ID).is(programId);
    val c1 = where(Program.PASSABILITY_SEM_NUM).is(semesterNumber);

    Update update = new Update();
    update.set(Program.PASSABILITY_SEM_START_DATE, startYear);
    update.set(Program.PASSABILITY_SEM_END_DATE, endYear);

    return repo.findAndModify(
        Query.query(new Criteria().andOperator(c2, c1)), update, Program.class);
  }

  public Mono<UpdateResult> deleteSemesterFromProgramList(String programId, String semesterId) {
    val c1 = where(Program.ID).is(programId);
    Update update =
        new Update()
            .pull(
                Program.PASSABILIES, Query.query(where(Passabilities.SEMESTER_ID).is(semesterId)));
    return repo.updateMulti(new Query(c1), update, Program.class);
  }

  public Mono<Program> getProgram(RequestDto requestDto) {
    return departmentService
        .checkForProgram(
            requestDto.getDepartmentId(), requestDto.getFacultyId(), requestDto.getUniversityId())
        .handle(
            ((exist, sink) -> {
              if (exist) {
                Program pr =
                    Program.builder()
                        .id(requestDto.getId())
                        .universityId(requestDto.getUniversityId())
                        .facultyId(requestDto.getFacultyId())
                        .departmentId(requestDto.getDepartmentId())
                        .name(requestDto.getName())
                        .level(requestDto.getLevel())
                        .semesterNumber(requestDto.getSemesterNumber())
                        .passabilies(requestDto.getPassabilities())
                        .build();
                sink.next(pr);
              } else {
                sink.error(
                    new BadRequestException(
                        "Probably you may have problem with id of "
                            + "university, faculty or department"));
              }
            }));
  }

  public Mono<Boolean> exists(String id) {
    return repo.exists(Query.query(where(Program.ID).is(id)), Program.class);
  }

  public Mono<Program> checkForProgram(Program program) {
    val c1 = where(Program.ID).is(program.getId());
    val c2 = where(Program.UNIVERSITY_ID).is(program.getUniversityId());
    val c3 = where(Program.NAME).is(program.getName());
    val c4 = where(Program.DEPARTMENT_ID).is(program.getDepartmentId());
    val c5 = where(Program.FACULTY_ID).is(program.getFacultyId());
    val c6 = where(Program.LEVEL).is(program.getLevel());

    return repo.findOne(
            Query.query(new Criteria().andOperator(c1, c2, c3, c4, c5, c6)), Program.class)
        .switchIfEmpty(
            Mono.error(
                new BadRequestException(
                    "Bad request the given program " + "is not found in our database")));
  }

  public Mono<Semester> checkForSemesterNumber(Semester semester) {
    val c1 =
        where(Program.SEM_NUMBER)
            .gte(semester.getNumber())
            .and(Program.PASSABILITIES_SEM_ID)
            .ne(semester.getId());

    return repo.exists(Query.query(c1), Program.class)
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.next(semester);
              else sink.error(new BadRequestException("This semester can't be added to database"));
            });
  }

  public Mono<Passabilities> getSemesterFromProgram(String semesterId, String programId) {
    Aggregation aggregation =
        newAggregation(
            match(where("_id").is(programId)),
            unwind("passabilies"),
            match(where("passabilies.semester._id").is(new ObjectId(semesterId))),
            project("passabilies.semester", "passabilies.passability", "passabilies.ects"));
    return repo.aggregate(aggregation, Program.class, Passabilities.class)
        .single()
        .switchIfEmpty(
            Mono.error(
                new BadRequestException("This semester id does not exist in program database")));
  }

  public Mono<Program> checkForStudentProgram(StudentRequestDto student) {
    val c1 = where(Program.UNIVERSITY_ID).is(student.getUniversityId());
    val c2 = where(Program.FACULTY_ID).is(student.getFacultyId());
    val c3 = where(Program.DEPARTMENT_ID).is(student.getDepartmentId());
    val c4 = where(Program.ID).is(student.getProgramId());

    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2, c3, c4)), Program.class)
        .switchIfEmpty(
            Mono.error(
                new BadRequestException(
                    "Student details such universityId"
                        + " facultyId, departmentId and programId are incorrect")));
  }

  public <T extends BasicCollegeInformation> Mono<Program> checkIfGivenEntityMatchesProgram(
      T information) {
    val c1 =
        Criteria.where(Program.UNIVERSITY_ID)
            .is(information.getUniversityId())
            .and(Program.FACULTY_ID)
            .is(information.getFacultyId())
            .and(Program.DEPARTMENT_ID)
            .is(information.getDepartmentId())
            .and(Program.ID)
            .is(information.getProgramId());

    return repo.findOne(Query.query(c1), Program.class)
        .switchIfEmpty(
            Mono.error(
                new BadRequestException(
                    "Details such universityId"
                        + " facultyId, departmentId and programId may be incorrect")));
  }
}
