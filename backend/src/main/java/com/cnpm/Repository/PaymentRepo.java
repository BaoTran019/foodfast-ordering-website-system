package com.cnpm.Repository;

import com.cnpm.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByPaymentId(int paymentId);

    Optional<Payment> findByOrderId(int orderId);
}
