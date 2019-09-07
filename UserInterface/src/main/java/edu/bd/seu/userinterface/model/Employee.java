package edu.bd.seu.userinterface.model;

import com.vaadin.flow.component.polymertemplate.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Employee {
    @Id
    private String id;
    private String name;
    private String role;
    private String email;
}
