package bd.edu.seu.programapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@Entity
public class Program {
    @Id
    private String name;
    private long minCreditReq;
    private long minCgpaReq;
    @OneToMany
    private List<Course> courseList;
}
