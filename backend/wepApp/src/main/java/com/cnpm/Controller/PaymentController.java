package com.cnpm.Controller;

import com.cnpm.DTO.PaymentResponse;
import com.cnpm.Entity.Payment;
import com.cnpm.Service.OrderService;
import com.cnpm.Service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    // Tạo Payment
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestParam int orderId,
            @RequestParam double amount,
            @RequestParam String method,
            HttpServletRequest request) {
        PaymentResponse response = paymentService.createPayment(orderId, amount, method, request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Lấy chi tiết Payment
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable int id) {
        return ResponseEntity.ok(paymentService.getPaymentByID(id));
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyVnPay(
            @RequestParam String vnp_TxnRef,
            @RequestParam String vnp_SecureHash,
            @RequestParam String vnp_ResponseCode
    ) {
        Map<String, Object> result = new HashMap<>();
        int orderId = Integer.parseInt(vnp_TxnRef);

        // TODO: Verify vnp_SecureHash ở đây để đảm bảo không bị giả mạo
        boolean hashValid = true; // placeholder, implement hash check theo VNPAY doc

        if (!hashValid) {
            result.put("success", false);
            result.put("message", "Hash không hợp lệ");
            return ResponseEntity.badRequest().body(result);
        }

        if ("00".equals(vnp_ResponseCode)) {
            // Update order/payment = PAID
            paymentService.updateStatus(orderId, "PAID");
            result.put("success", true);
            result.put("orderId", orderId);
            result.put("message", "Thanh toán thành công");
        } else {
            // Update order/payment = FAILED
            paymentService.updateStatus(orderId, "FAILED");
            result.put("success", false);
            result.put("orderId", orderId);
            result.put("message", "Thanh toán thất bại");
        }

        return ResponseEntity.ok(result);
    }

    // Cập nhật trạng thái Payment
    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable int id,
            @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updateStatus(id, status));
    }
}
