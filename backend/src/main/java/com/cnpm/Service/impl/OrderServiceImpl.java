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
import com.cnpm.Repository.PaymentRepo;
import com.cnpm.Repository.CartRepo;
import com.cnpm.Repository.ProductRepo;
import com.cnpm.Entity.Product;
import com.cnpm.Entity.Payment;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final PaymentRepo paymentRepo;
    
    @PersistenceContext
    private EntityManager entityManager;

    public OrderServiceImpl(OrderRepo orderRepo, CartRepo cartRepo, ProductRepo productRepo, PaymentRepo paymentRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(int id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String status) {
        List<Order> orders = orderRepo.findByStatus(status);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO saveOrder(OrderDTO order) {
        Order entity = convertToEntity(order);
        return convertToDTO(orderRepo.save(entity));
    }

    @Override
    public void deleteOrderById(int id) {
        orderRepo.deleteById(id);
    }

    @Override
    public OrderDTO updateOrderStatus(int orderId, String status) {
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepo.save(order);
        return convertToDTO(order);
    }

    // Tạo order từ cart
    @Override
    @Transactional
    public OrderDTO createOrderFromCart(int userId, String payment_method, String recipientName, String recipientPhone, String shipping_address) {
        Cart cart = cartRepo.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(cart.getUser().getUserId());
        for (CartItem cartItem : cart.getCartItems()) {
            order.addOrderItems(cartItem.getProduct(), cartItem.getQuantity(), cartItem.getProduct().getPrice());
        }
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        order.setRecipientName(recipientName);
        order.setRecipientPhone(recipientPhone);
        order.setDeliveryAddress(shipping_address);
        order.setTotalPrice(cart.getTotalPrice());
        order = orderRepo.save(order);

        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setPaymentMethod(payment_method);
        payment.setAmount(order.getTotalPrice());
        payment.setStatus("UNPAID");
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepo.save(payment);
        // Đảm bảo entity được cập nhật đầy đủ
        entityManager.refresh(order);
        // Xóa giỏ hàng sau khi tạo đơn hàng
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepo.save(cart);
        return convertToDTO(order);
    }

    // Helper: convert Order Entity -> OrderDTO
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setOrderDate(order.getOrderDate());
        dto.setRecipientName(order.getRecipientName());
        dto.setRecipientPhone(order.getRecipientPhone());
        dto.setOrderItems(order.getOrderItems()
                .stream()
                .map(item -> new ItemDTO(
                        item.getProduct().getProductId(),
                        item.getProduct().getProductName(), // include product name in DTO
                        item.getProduct().getImageUrl(), // include image URL in DTO
                        item.getQuantity(),
                        item.getProduct().getPrice() // include price in DTO
                ))
                .collect(Collectors.toList()));
        Payment payment = paymentRepo.findByOrderId(order.getOrderId()).orElse(null);
        dto.setPaymentMethod(payment != null ? payment.getPaymentMethod() : null);
        dto.setPaymentStatus(payment != null ? payment.getStatus() : null);

        return dto;
    }

    // Helper: convert OrderDTO -> Order Entity
    private Order convertToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        order.setUserId(dto.getUserId());
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalPrice(dto.getTotalPrice());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setRecipientName(dto.getRecipientName());
        order.setRecipientPhone(dto.getRecipientPhone());
        for (ItemDTO itemDTO : dto.getOrderItems()) {
            // Giả sử bạn có một phương thức để tìm Product theo ID
            Product product = productRepo.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            order.addOrderItems(product, itemDTO.getQuantity(), product.getPrice());
        }
        return order;
    }
}
