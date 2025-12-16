package com.cnpm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private int productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double price; // Price at the time of adding to cart

}

