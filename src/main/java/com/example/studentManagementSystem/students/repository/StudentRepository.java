package com.example.studentManagementSystem.students.repository;

import com.example.studentManagementSystem.courses.service.CourseService;
import com.example.studentManagementSystem.deadline.service.DeadlineService;
import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.programs.service.ProgramService;
import com.example.studentManagementSystem.semester.entity.Semester;
import com.example.studentManagementSystem.students.dto.*;
import com.example.studentManagementSystem.students.entity.Student;
import com.example.studentManagementSystem.students.entity.SubmittedCourse;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;

@Repository
@AllArgsConstructor
public class StudentRepository {
  private final ReactiveMongoTemplate repo;
  private final ProgramService programService;
  private final CourseService courseService;
  private final DeadlineService deadlineService;

  public Mono<ResponseDto> addStudent(StudentRequestDto studentDto) {
    studentDto.setSemesterList(new ArrayList<>());
    studentDto.setListOfCourses(new ArrayList<>());
    studentDto.setSubmittedCoursesList(new ArrayList<>());
    studentDto.setTotalEtcs(0);

    return checkForStudent(studentDto)
        .flatMap(objectToSave -> repo.insert(objectToSave, "Students"))
        .map(
            (Student requestDto) ->
                ResponseDto.builder()
                    .name(requestDto.getName())
                    .surname(requestDto.getSurname())
                    .personalNumber(requestDto.getPersonalNumber())
                    .birthdayDate(requestDto.getBirthdayDate())
                    .gender(requestDto.getGender())
                    .email(requestDto.getEmail())
                    .password(requestDto.getPassword())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .university(requestDto.getUniversityId())
                    .faculty(requestDto.getFacultyId())
                    .department(requestDto.getDepartmentId())
                    .program(requestDto.getProgramId())
                    .totalEtcs(requestDto.getTotalEcts())
                    .build());
  }

  public Mono<Student> checkForStudent(StudentRequestDto studentRequestDto) {
    return programService
        .checkForStudentProgram(studentRequestDto)
        .flatMap(
            program ->
                repo.exists(
                    Query.query(
                        Criteria.where(Student.PERSONAL_NUMBER)
                            .is(studentRequestDto.getPersonalNumber())),
                    Student.class))
        .handle(
            (exists, sink) -> {
              if (exists)
                sink.error(
                    new BadRequestException(
                        "Bad request this student with"
                            + " that personalNumber actually exists in our database"));
              else
                sink.next(
                    Student.builder()
                        .name(studentRequestDto.getName())
                        .surname(studentRequestDto.getSurname())
                        .personalNumber(studentRequestDto.getPersonalNumber())
                        .birthdayDate(studentRequestDto.getBirthdayDate())
                        .gender(studentRequestDto.getGender())
                        .password(studentRequestDto.getPassword())
                        .phoneNumber(studentRequestDto.getPhoneNumber())
                        .universityId(studentRequestDto.getUniversityId())
                        .facultyId(studentRequestDto.getFacultyId())
                        .departmentId(studentRequestDto.getDepartmentId())
                        .programId(studentRequestDto.getProgramId())
                        .semesterList(studentRequestDto.getSemesterList())
                        .listOfCourses(studentRequestDto.getListOfCourses())
                        .submittedCoursesList(studentRequestDto.getSubmittedCoursesList())
                        .totalEcts(studentRequestDto.getTotalEtcs())
                        .build());
            });
  }
  // qeta duhet me e kontrollu edhe a ti ka dhan kredit e studentit mire se mundet me i fake
  public Mono<Student> addAndUpdateSemester(
      Integer studentPersonalNumber, String programId, String semesterId, Integer studentEtcs) {

    return programService
        .getSemesterFromProgram(semesterId, programId)
        .flatMap(
            passabilities -> {
              if (passabilities.getSemester().getNumber() == 1) {
                return addOrUpdateSemester(studentPersonalNumber, passabilities.getSemester());
              } else {
                if (passabilities.getSemester().getNumber() % 2 != 0) {
                  if (passabilities.getPassability() <= studentEtcs) {
                    return addOrUpdateSemester(studentPersonalNumber, passabilities.getSemester());
                  } else {
                    return Mono.error(
                        new BadRequestException(
                            "Student has not enough ects to register this semester"));
                  }
                } else
                  return checkSemesterNumber(passabilities.getSemester())
                      .flatMap(
                          semester ->
                              addOrUpdateSemester(
                                  studentPersonalNumber, passabilities.getSemester()));
              }
            });
  }

  public Mono<Semester> checkSemesterNumber(Semester semester) {
    val c1 = Criteria.where(Student.SEMESTER_NUMBER).is(semester.getNumber() - 1);
    return repo.exists(Query.query(c1), Student.class)
        .handle(
            (aBoolean, sink) -> {
              if (aBoolean) sink.next(semester);
              else
                sink.error(
                    new BadRequestException(
                        "This semester is more then 1 level greater than last semester"));
            });
  }

  public Mono<Student> addOrUpdateSemester(Integer studentPersonalNumber, Semester semester) {
    val c1 = Criteria.where("semesterList.semester._id").is(new ObjectId(semester.getId()));
    val c2 = Criteria.where(Student.PERSONAL_NUMBER).is(studentPersonalNumber);
    val c3 =
        Criteria.where("semesterList.semester.startYear")
            .ne(semester.getStartYear())
            .and("semesterList.semester.endYear")
            .ne(semester.getEndYear());
    Update update = new Update();

    return repo.exists(Query.query(c1), Student.class)
        .flatMap(
            aBoolean -> {
              if (aBoolean) {
                update.set(Student.UPDATE_SEMESTER_START_DATE, semester.getStartYear());
                update.set(Student.UPDATE_SEMESTER_END_DATE, semester.getEndYear());
                update.inc("semesterList.$[elem].count", 1);
                update.filterArray("elem.semesterId", semester.getId());
                return repo.findAndModify(
                    Query.query(new Criteria().andOperator(c1, c2, c3)), update, Student.class);
              } else {
                StudentSemesters semesters = new StudentSemesters(semester.getId(), semester, 1);
                update.push(Student.LIST_OF_SEMESTERS, semesters);
                return repo.findAndModify(Query.query(c2), update, Student.class);
              }
            });
  }

  public Mono<UpdateResult> addCourse(Integer studentPersonalNumber, String courseId) {
    val c3 = Criteria.where(Student.PERSONAL_NUMBER).is(studentPersonalNumber);
    Update update = new Update();
    return courseService
        .getCourseById(courseId)
        .flatMap(
            course1 -> {
              val c1 = Criteria.where(Student.PROGRAM_ID).is(course1.getProgramId());
              val c2 = Criteria.where(Student.SEMESTER_ID).is(course1.getSemesterId());
              update.addToSet(Student.LIST_OF_COURSES, course1);
              return repo.updateFirst(
                  Query.query(new Criteria().andOperator(c1, c2, c3)), update, Student.class);
            });
  }
  // duhet me e caktu edhe daten e paraqitjes se provimit
  // duhet me e kqyre kur te jep provimin me ja rrit studentit ects
  public Mono<Student> submitCourse(
      Integer studentPersonalNumber, SubmittedCourse submittedCourse) {
    val c1 = Criteria.where(Student.PERSONAL_NUMBER).is(studentPersonalNumber);
    val v1 =
        Criteria.where(Student.LIST_OF_COURSES_ID).is(new ObjectId(submittedCourse.getCourseId()));
    val v2 =
        Criteria.where(Student.SUBMITTED_COURSE_LIST_COURSE_ID).ne(submittedCourse.getCourseId());
    val v3 =
        Criteria.where(Student.SUBMITTED_COURSE_LIST)
            .elemMatch(
                Criteria.where(Student.DEADLINE_ID)
                    .is(submittedCourse.getDeadlineId())
                    .and(Student.COURSE_ID)
                    .is(submittedCourse.getCourseId()));
    val v4 =
        Criteria.where(Student.SUBMITTED_COURSE_LIST_COURSE_ID).is(submittedCourse.getCourseId());
    Update update = new Update();
    return deadlineService
        .checkIfSubmittedCourseIsValid(Instant.now(), submittedCourse.getDeadlineId())
        .flatMap(
            aBoolean -> {
              if (aBoolean) {
                return repo.exists(Query.query(v1), Student.class)
                    .flatMap(
                        aBoolean1 -> {
                          if (aBoolean1) {
                            return repo.exists(Query.query(v2), Student.class)
                                .flatMap(
                                    aBoolean2 -> {
                                      if (aBoolean2) {
                                        submittedCourse.setCount(1);
                                        submittedCourse.setSubmittedDate(Instant.now());
                                        submittedCourse.setGrade(null);
                                        submittedCourse.setGradePlacedDate(null);
                                        submittedCourse.setStatus(false);
                                        update.push(Student.SUBMITTED_COURSE_LIST, submittedCourse);
                                        return repo.findAndModify(
                                            Query.query(c1), update, Student.class);
                                      } else {
                                        return repo.exists(Query.query(v3), Student.class)
                                            .flatMap(
                                                aBoolean3 -> {
                                                  if (aBoolean3) {
                                                    return Mono.error(
                                                        new BadRequestException(
                                                            "This course exam actually is submitted"));

                                                  } else {
                                                    update.set(
                                                        Student.UPDATE_DEADLINE_ID,
                                                        submittedCourse.getDeadlineId());
                                                    update.inc("submittedCoursesList.$.count", 1);
                                                    return repo.findAndModify(
                                                        Query.query(
                                                            new Criteria().andOperator(c1, v4)),
                                                        update,
                                                        Student.class);
                                                  }
                                                });
                                      }
                                    });
                          } else {
                            return Mono.error(
                                new BadRequestException("This course is not registered by user"));
                          }
                        });

              } else return Mono.error(new BadRequestException("The deadline closed"));
            });
  }
}
