package com.example.studentManagementSystem.faculty.dto;


import com.example.studentManagementSystem.faculty.entity.Faculty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AddDto {
    @Id
    private String id;
    private Faculty faculty;
    private String name;

}
