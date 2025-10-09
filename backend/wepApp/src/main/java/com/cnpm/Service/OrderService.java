package com.cnpm.Service;

import com.cnpm.Entity.Order;
import com.cnpm.DTO.OrderDTO;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    List<Order> getAllOrders();

    OrderDTO getOrderById(int id);

    Order saveOrder(Order order);

    void deleteOrderById(int id);
    
    void updateOrderStatus(int orderId, String status);

    OrderDTO createOrderFromCart(int cartId, String payment, String paymentMethod, String shipping_address);
}
