package edu.bd.seu.userinterface.model;


import com.vaadin.flow.component.polymertemplate.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})

public class Student {
    @Id
    private String id;
    private String name;
    private String email;
    private LocalDate dob;
    private long batch;
    private LocalDate dateOfAdmission;
    private String Program;
    private Double cgpa;
    private long completedCredit;
    private List<Course> courseList;

    public Student(String id, String name, String email, LocalDate dob, long batch, LocalDate dateOfAdmission, String program) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.batch = batch;
        this.dateOfAdmission = dateOfAdmission;
        Program = program;
        List<Course> courses = new ArrayList<>();
        this.courseList = courses;
        this.cgpa = 0.0;
        this.completedCredit = 0;

    }
}



