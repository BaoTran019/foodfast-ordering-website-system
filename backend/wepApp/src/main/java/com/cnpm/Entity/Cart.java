package com.cnpm.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Carts") // tên Table trong database
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private int cartId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "totalPrice")
    private double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    // Phương thức thêm CartItem vào Cart
    public void addCartItem(Product product, int quantity) {
        CartItem newItem = new CartItem();
        newItem.setCart(this);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setPrice(product.getPrice());
        this.cartItems.add(newItem);
    }

}
