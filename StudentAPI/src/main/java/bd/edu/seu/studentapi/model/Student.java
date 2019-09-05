package bd.edu.seu.studentapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.time.LocalDate;
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
}
