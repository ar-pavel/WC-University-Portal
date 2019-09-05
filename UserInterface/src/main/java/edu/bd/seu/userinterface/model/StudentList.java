package edu.bd.seu.userinterface.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class StudentList {
    private List<Student> studentList;
    public StudentList() {
        this.studentList = studentList;
    }

}
