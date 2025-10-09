package com.cnpm.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;

@Entity
@Table(name = "Orders") // tên Table trong database
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "orderDate", insertable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "status")
    private String status; // PENDING, SHIPPED, DELIVERED, CANCELED

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "payment")
    private String payment; // PAID / UNPAID

    @Column(name = "paymentMethod")
    private String paymentMethod; // COD / VNPAY / MOMO

    @Column(name = "shipping_address")
    private String shipping_address;

    // Quan hệ 1 Order có nhiều OrderItems
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    // Constructors
    public Order() {
    }

    public Order(int id, int userId, LocalDateTime orderDate, String status, double totalPrice, String payment,
            String paymentMethod, String shipping_address) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.paymentMethod = paymentMethod;
        this.shipping_address = shipping_address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void addOrderItems(Product product, int quantity) {
        OrderItem orderItem = new OrderItem(this, product, quantity, product.getPrice());
        orderItems.add(orderItem);
    }

}
