package bd.edu.seu.convocationapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"studentId"})
public class Convocation {
    private String studentId;
    private String transactionId;
    private String paymentStatus;
    private String confirmationStatus;
}
