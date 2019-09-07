package bd.edu.seu.programapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


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
}
