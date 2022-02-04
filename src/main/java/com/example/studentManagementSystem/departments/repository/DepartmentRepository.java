package com.example.studentManagementSystem.departments.repository;

import com.example.studentManagementSystem.departments.dto.AddDepRequestDto;
import com.example.studentManagementSystem.departments.dto.AddResponseDto;
import com.example.studentManagementSystem.departments.entity.Department;
import com.example.studentManagementSystem.exception.BadRequestException;
import com.example.studentManagementSystem.faculty.entity.Faculty;
import com.example.studentManagementSystem.faculty.service.FacultyService;
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
public class DepartmentRepository {

  private final ReactiveMongoTemplate repo;
  private final FacultyService facultyService;

  public Mono<AddResponseDto> addDepartment(AddDepRequestDto department) {
    return getDepartment(department)
        .flatMap(repo::insert)
        .map(
            department1 ->
                AddResponseDto.builder()
                    .id(department1.getId())
                    .universityId(department1.getUniversityId())
                    .facultyId(department1.getFacultyId())
                    .name(department1.getName())
                    .build());
  }

  public Mono<Department> getDepartment(AddDepRequestDto department) {
    return facultyService
        .checkForUniversityId(department.getFacultyId(), department.getUniversityId())
        .handle(
            (exists, sink) -> {
              if (exists) {
                Department department1 =
                    Department.builder()
                        .id(department.getId())
                        .facultyId(department.getFacultyId())
                        .universityId(department.getUniversityId())
                        .name(department.getName())
                        .build();
                sink.next(department1);
              } else {
                sink.error(
                    new BadRequestException(
                        "Probably you have typed bad faculty id or university id "
                            + "or they may be not in our database"));
              }
            });
  }

  public Mono<Department> findDepartmentByName(String name, String facultyId) {
    val c1 = Criteria.where(Department.DEPARTMENT_NAME).is(name);
    val c2 = Criteria.where(Department.DEPARTMENT_FACULTY_ID).is(facultyId);

    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2)), Department.class);
  }

  public Mono<DeleteResult> deleteDepartment(String name, String facultyId) {
    val c1 = Criteria.where(Department.DEPARTMENT_NAME).is(name);
    val c2 = Criteria.where(Department.DEPARTMENT_FACULTY_ID).is(facultyId);

    return repo.remove(Query.query(new Criteria().andOperator(c1, c2)), Department.class);
  }

  public Mono<Boolean> checkForProgram(String depId, String facultyId, String universityId) {
    val c1 = Criteria.where(Department.DEPARTMENT_ID).is(depId);
    val c2 = Criteria.where(Department.DEPARTMENT_FACULTY_ID).is(facultyId);
    val c3 = Criteria.where(Department.DEPARTMENT_UNIVERSITY_ID).is(universityId);

    return repo.exists(Query.query(new Criteria().andOperator(c1, c2, c3)), Department.class);
  }

  public Mono<Department> checkForDepartment(Department department) {
    val c1 = Criteria.where(Department.DEPARTMENT_ID).is(department.getId());
    val c2 = Criteria.where(Department.DEPARTMENT_UNIVERSITY_ID).is(department.getUniversityId());
    val c3 = Criteria.where(Department.DEPARTMENT_NAME).is(department.getName());
    val c4 = Criteria.where(Department.DEPARTMENT_FACULTY_ID).is(department.getFacultyId());

    return repo.findOne(Query.query(new Criteria().andOperator(c1, c2, c3, c4)), Department.class)
        .switchIfEmpty(
            Mono.error(
                new BadRequestException(
                    "Bad request the given department " + "is not found in our database")));
  }
}
