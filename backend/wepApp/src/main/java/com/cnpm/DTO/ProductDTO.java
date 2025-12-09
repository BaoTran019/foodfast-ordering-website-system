package com.cnpm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productName;
    private String description;
    private double price;
    private String imageUrl;
    private boolean available; // isAvailable
    private int categoryId;
}
