package com.cnpm.Repository;

import com.cnpm.Entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.product.productId = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.product.productId = :productId")
    void deleteByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId")
    void deleteByCartId(@Param("cartId") int cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.product.productId = :productId")
    void deleteByProductId(@Param("productId") int productId);

}
