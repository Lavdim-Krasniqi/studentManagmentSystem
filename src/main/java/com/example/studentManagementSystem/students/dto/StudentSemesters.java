package com.example.studentManagementSystem.students.dto;


import com.example.studentManagementSystem.semester.entity.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentSemesters {

    private String semesterId;
    private Semester semester;
    private Integer count;
}
