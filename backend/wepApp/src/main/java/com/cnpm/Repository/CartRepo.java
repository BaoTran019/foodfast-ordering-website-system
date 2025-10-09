package com.cnpm.Repository;

import com.cnpm.Entity.Cart;
import com.cnpm.Entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    Optional<Cart> findById(Integer id);

    Optional<Cart> findByUserId(int userId);

    Optional<CartItem> findCartItemById(int itemId);

    
}
