package com.cnpm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.cnpm.Entity.OrderItem;

@Repository 
public interface OrderitemRepo extends JpaRepository<OrderItem, Integer> {
    Optional<OrderItem> findByOrderItemId(Integer orderItemId);

}
