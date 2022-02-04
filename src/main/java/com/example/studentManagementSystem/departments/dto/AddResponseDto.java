package com.example.studentManagementSystem.departments.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddResponseDto {

    private String id;
    private String universityId;
    private String facultyId;
    private String name;
}
