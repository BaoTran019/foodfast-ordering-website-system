package com.cnpm.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private int userId;
    private LocalDateTime orderDate;
    private String status;
    private double totalPrice;
    private String deliveryAddress;
    private String recipientName;
    private String recipientPhone;
    private List<ItemDTO> orderItems;
    private String paymentMethod;
    private String paymentStatus;
}
