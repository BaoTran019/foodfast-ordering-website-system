package com.cnpm.Repository;

import com.cnpm.Entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findById(Integer id);

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

    void deleteByCartIdAndProductId(int cartId, int productId);

    void deleteByCartId(int cartId);

    void deleteByProductId(int productId);

}
