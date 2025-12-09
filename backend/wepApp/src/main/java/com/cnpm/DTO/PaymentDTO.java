package com.cnpm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private int paymentId;
    private int orderId;
    private double amount;
    private String paymentMethod;
    private String status;
}
