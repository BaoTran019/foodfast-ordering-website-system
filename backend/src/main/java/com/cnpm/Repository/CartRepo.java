package com.cnpm.Repository;

import com.cnpm.Entity.Cart;
import com.cnpm.Entity.CartItem;
import com.cnpm.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByCartId(Integer cartId);

    Optional<Cart> findByUserUserId(int userId);

    Optional<Cart> findByUser(User user);

    
}
