package edu.bd.seu.userinterface.model;


import com.vaadin.flow.component.polymertemplate.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String transactionId;
}


