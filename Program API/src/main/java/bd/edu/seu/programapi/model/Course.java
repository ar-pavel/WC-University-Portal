package bd.edu.seu.programapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"code"})
@Entity
public class Course {
    @Id
    private String code;
    private String title;
    private Double credit;
}
