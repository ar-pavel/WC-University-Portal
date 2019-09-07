package bd.edu.wcu.authserver.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})

public class LoginToken {
    @Id
    private String username;
    private String name;
    private String fullName;
    private String role;
}