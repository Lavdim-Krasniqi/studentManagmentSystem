package com.example.studentManagementSystem.departments.controller;

import com.example.studentManagementSystem.departments.dto.AddDepRequestDto;
import com.example.studentManagementSystem.departments.dto.AddResponseDto;
import com.example.studentManagementSystem.departments.entity.Department;
import com.example.studentManagementSystem.departments.service.DepartmentService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/department", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {
  private final DepartmentService service;

  @PostMapping("/addOne")
  public Mono<AddResponseDto> addDepartment(@RequestBody AddDepRequestDto departmentDto) {
    return service.addDepartment(departmentDto);
  }

  @GetMapping("/findOne/{name}/{facultyId}")
  public Mono<Department> findDepartmentByName(
      @PathVariable String name, @PathVariable String facultyId) {
    return service.findDepartmentByName(name, facultyId);
  }

  @DeleteMapping("/deleteOne/{name}/{facultyId}")
  public Mono<DeleteResult> deleteDepartment(
      @PathVariable String name, @PathVariable String facultyId) {
    return service.deleteDepartment(name, facultyId);
  }
}
