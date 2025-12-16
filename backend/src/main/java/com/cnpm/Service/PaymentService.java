package com.cnpm.Service;

import com.cnpm.DTO.PaymentResponse;
import com.cnpm.Entity.Payment;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
public interface PaymentService {
    PaymentResponse createPayment(int orderId, double amount, String method, HttpServletRequest request);
    
    Payment updateStatus(int id, String status);

    Payment getPaymentByID(int id);

}
