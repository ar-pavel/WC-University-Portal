package edu.bd.seu.userinterface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginToken {
    private String username;
    private String name;
    private String fullName;
    private String role;

    public LoginToken() {
        role = "norole";
    }
}