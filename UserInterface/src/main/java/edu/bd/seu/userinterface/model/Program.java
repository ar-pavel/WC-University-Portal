package edu.bd.seu.userinterface.model;

import com.vaadin.flow.component.polymertemplate.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Program {
    @Id
    private String name;
    private double minCreditReq;
    private double minCgpaReq;
    private List<Course> courseList;

    public Program(String name, double minCreditReq, double minCgpaReq) {
        this.name = name;
        this.minCreditReq = minCreditReq;
        this.minCgpaReq = minCgpaReq;
        courseList = new ArrayList<>();
    }
}