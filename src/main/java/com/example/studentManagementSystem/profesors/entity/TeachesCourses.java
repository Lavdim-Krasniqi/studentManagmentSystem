package com.example.studentManagementSystem.profesors.entity;

import com.example.studentManagementSystem.baseEntities.BasicCollegeInformation;
import com.example.studentManagementSystem.courses.entity.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TeachesCourses extends BasicCollegeInformation {

  private Course course;

}
