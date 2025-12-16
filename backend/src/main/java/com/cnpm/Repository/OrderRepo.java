package com.cnpm.Repository;

import com.cnpm.Entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer>{

    Optional<Order> findByOrderId(Integer orderId);

    List<Order> findByStatus(String status);

}