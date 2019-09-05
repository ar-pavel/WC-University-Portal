package edu.bd.seu.userinterface.model;

import com.vaadin.flow.component.polymertemplate.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"code"})
public class Course {
    @Id
    private String code;
    private String title;
    private Double credit;
}
