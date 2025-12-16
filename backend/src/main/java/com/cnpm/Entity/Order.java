package com.cnpm.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders") // tên Table trong database
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "orderDate", insertable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "status")
    private String status; // PENDING, SHIPPED, DELIVERED, CANCELED

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "deliveryAddress")
    private String deliveryAddress;

    @Column(name = "recipientName")
    private String recipientName;

    @Column(name = "recipientPhone")
    private String recipientPhone;

    // Quan hệ 1 Order có nhiều OrderItems
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    // Phương thức thêm OrderItem vào Order
    public void addOrderItems(Product product, int quantity, double price) {
        OrderItem newItem = new OrderItem();
        newItem.setOrder(this);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setPrice(price);
        this.orderItems.add(newItem);
        this.totalPrice += price * quantity; // Cập nhật tổng giá khi thêm sản phẩm
    }
}
