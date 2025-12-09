package com.cnpm.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId")
    private int paymentId;

    @Column(name = "orderId")
    private int orderId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "paymentMethod")
    private String paymentMethod;   // "COD", "VNPAY", "MOMO"

    @Column(name = "status")
    private String status;   // "PAID", "UNPAID", "FAILED"

    @Column(name = "paymentDate")
    private LocalDateTime paymentDate;

}
