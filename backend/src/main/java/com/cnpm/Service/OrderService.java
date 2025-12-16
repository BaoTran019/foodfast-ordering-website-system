package com.cnpm.Service;

import com.cnpm.Entity.Order;
import com.cnpm.DTO.OrderDTO;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(int id);

    OrderDTO saveOrder(OrderDTO order);

    List<OrderDTO> getOrdersByStatus(String status);

    void deleteOrderById(int id);

    OrderDTO updateOrderStatus(int orderId, String status);

    OrderDTO createOrderFromCart(int cartId, String payment_method, String recipientName, String recipientPhone, String shipping_address);
}
