package com.example.studentManagementSystem.university.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;

@Data
@CompoundIndex(name = "university_index", def = "{'name':1, 'state':1, 'city':1}", unique = true)
@Document("University")
public class University {
    public static final String UNIVERSITY_ID = "id";
    public static final String UNIVERSITY_NAME = "name";
    public static final String UNIVERSITY_STATE = "state";
    public static final String UNIVERSITY_CITY = "city";

    @Id
    private String id;
    @NotBlank(message = "Name of university should not be blank")
    private String name;
    @NotBlank(message = "State to which university belongs should not be blank")
    private String state;
    @NotBlank(message = "City to which university belongs should not be blank")
    private String city;
}
