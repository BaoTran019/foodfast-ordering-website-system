package com.cnpm.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private int cartId;
    private int userId;
    private double totalPrice;
    private List<ItemDTO> cartItems;

    
}
