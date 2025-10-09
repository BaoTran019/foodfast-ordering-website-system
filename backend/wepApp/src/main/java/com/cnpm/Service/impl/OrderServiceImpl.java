package com.cnpm.Service.impl;

import com.cnpm.Service.OrderService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

import com.cnpm.DTO.ItemDTO;
import com.cnpm.DTO.OrderDTO;
import com.cnpm.Entity.CartItem;
import com.cnpm.Entity.Cart;
import com.cnpm.Entity.Order;
import com.cnpm.Repository.OrderRepo;
import com.cnpm.Repository.CartRepo;
import com.cnpm.Repository.ProductRepo;
import com.cnpm.Entity.Product;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    
    @PersistenceContext
    private EntityManager entityManager;

    public OrderServiceImpl(OrderRepo orderRepo, CartRepo cartRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public OrderDTO getOrderById(int id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToDTO(order);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public void deleteOrderById(int id) {
        orderRepo.deleteById(id);
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        OrderDTO order = getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        order.setStatus(status);
        orderRepo.save(convertToEntity(order));
    }

    // Tạo order từ cart
    @Override
    @Transactional
    public OrderDTO createOrderFromCart(int userId, String payment, String paymentMethod, String shipping_address) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(cart.getUserId());
        for (CartItem cartItem : cart.getCartItems()) {
            order.addOrderItems(cartItem.getProduct(), cartItem.getQuantity());
        }
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        order.setPayment(payment);
        order.setPaymentMethod(paymentMethod);
        order.setShipping_address(shipping_address);
        order.setTotalPrice(cart.getTotalPrice());
        order = orderRepo.save(order);
        entityManager.refresh(order);
        // Xóa giỏ hàng sau khi tạo đơn hàng
        cartRepo.delete(cart);
        return convertToDTO(order);
    }

    // Helper: convert Order Entity -> OrderDTO
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setPayment(order.getPayment());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setShipping_address(order.getShipping_address());
        dto.setOrderItems(order.getOrderItems()
                .stream()
                .map(item -> new ItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(), // include product name in DTO
                        item.getProduct().getImageUrl(), // include image URL in DTO
                        item.getQuantity(),
                        item.getPrice() // include price in DTO
                ))
                .collect(Collectors.toList()));

        return dto;
    }

    // Helper: convert OrderDTO -> Order Entity
    private Order convertToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getOrderId());
        order.setUserId(dto.getUserId());
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalPrice(dto.getTotalPrice());
        order.setPayment(dto.getPayment());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setShipping_address(dto.getShipping_address());
        for (ItemDTO itemDTO : dto.getOrderItems()) {
            // Giả sử bạn có một phương thức để tìm Product theo ID
            Product product = productRepo.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            order.addOrderItems(product, itemDTO.getQuantity());
        }
        return order;
    }
}
