package com.example.studentManagementSystem.departments.service;

import com.example.studentManagementSystem.departments.dto.AddDepRequestDto;
import com.example.studentManagementSystem.departments.dto.AddResponseDto;
import com.example.studentManagementSystem.departments.entity.Department;
import com.example.studentManagementSystem.departments.repository.DepartmentRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DepartmentService {
  private final DepartmentRepository repo;

  public Mono<AddResponseDto> addDepartment(AddDepRequestDto departmentDto) {

    return repo.addDepartment(departmentDto);
  }

  public Mono<Department> findDepartmentByName(String name, String facultyId) {
    return repo.findDepartmentByName(name, facultyId);
  }

  public Mono<DeleteResult> deleteDepartment(String name, String facultyId) {
    return repo.deleteDepartment(name, facultyId);
  }

  public Mono<Boolean> checkForProgram(String depId, String facultyId, String universityId) {
    return repo.checkForProgram(depId, facultyId, universityId);
  }

  public Mono<Department> checkForDepartment(Department department) {
    return repo.checkForDepartment(department);
  }
}
