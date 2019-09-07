package edu.bd.seu.userinterface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"studentId"})
public class Convocation {
    private String id;
    private String studentId;
    private String paymentStatus;
    private String confirmationStatus;

    public Convocation() {
        this.id = null;
        this.studentId = null;
        this.paymentStatus = null;
        this.confirmationStatus = null;
    }
}