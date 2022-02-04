package com.example.studentManagementSystem.departments.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import javax.validation.constraints.NotBlank;

@Data
public class AddDepRequestDto {
  @Id private String id;

  @NotBlank(message = "You should to add university id to which the department belongs")
  @Field(targetType = FieldType.OBJECT_ID)
  private String universityId;

  @NotBlank(message = "You should to add faculty id to which the department belongs")
  @Field(targetType = FieldType.OBJECT_ID)
  private String facultyId;

  @NotBlank(message = "Name of department shouldn't be empty")
  private String name;
}
