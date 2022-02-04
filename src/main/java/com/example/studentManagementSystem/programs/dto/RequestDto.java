package com.example.studentManagementSystem.programs.dto;

import com.example.studentManagementSystem.programs.entity.Passabilities;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RequestDto {

  @Id private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  @NotBlank(message = "UniversityId should not be blank")
  private String universityId;

  @Field(targetType = FieldType.OBJECT_ID)
  @NotBlank(message = "FacultyId should not be blank")
  private String facultyId;

  @Field(targetType = FieldType.OBJECT_ID)
  @NotBlank(message = "DepartmentId should not be blank")
  private String departmentId;

  @NotBlank(message = "Name field should not be blank")
  private String name;

  @NotBlank(message = "Level field should not be blank")
  private String level;

  @NotBlank(message = "SemesterNumber field should not be blank")
  private Integer semesterNumber;

  private List<Passabilities> passabilities;
}
