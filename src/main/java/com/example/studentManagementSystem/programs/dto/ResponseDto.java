package com.example.studentManagementSystem.programs.dto;

import com.example.studentManagementSystem.programs.entity.Passabilities;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseDto {

    private String id;
    private String universityId;
    private String facultyId;
    private String departmentId;
    private String name;
    private String level;
    private Integer semesterNumber;
    private List<Passabilities> passabilies;
}
