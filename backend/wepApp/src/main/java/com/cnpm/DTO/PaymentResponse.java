package com.cnpm.DTO;

import com.cnpm.Entity.Payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private boolean success;
    private String message;
    private Payment payment;
    private String paymentUrl; // nếu muốn gửi kèm URL

    // Constructor
    public PaymentResponse(boolean success, String message, Payment payment, String paymentUrl) {
        this.success = success;
        this.message = message;
        this.payment = payment;
        this.paymentUrl = paymentUrl;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Payment getPayment() { return payment; }
    public String getPaymentUrl() { return paymentUrl; }
}
