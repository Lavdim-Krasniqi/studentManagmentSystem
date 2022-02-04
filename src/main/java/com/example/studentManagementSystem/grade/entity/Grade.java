package com.example.studentManagementSystem.grade.entity;


import com.example.studentManagementSystem.courses.entity.Course;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Document("Grade")
@Data
public class Grade {

    @Id
    private String id;
    @Min(value = 5)
    @Max(value = 10)
    private Integer grade;
    private Course course;
    private Instant setDate;
}
