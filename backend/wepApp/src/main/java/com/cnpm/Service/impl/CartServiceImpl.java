package com.cnpm.Service.impl;

import com.cnpm.Service.CartService;
import com.cnpm.DTO.CartDTO;
import com.cnpm.DTO.ItemDTO;
import com.cnpm.Entity.Cart;
import com.cnpm.Entity.CartItem;
import com.cnpm.Entity.Product;
import com.cnpm.Entity.User;
import com.cnpm.Repository.CartRepo;
import com.cnpm.Repository.ProductRepo;
import com.cnpm.Repository.UserRepo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public CartServiceImpl(CartRepo cartRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    // Lấy toàn bộ carts → trả về DTO
    @Override
    public List<CartDTO> getAllCarts() {
        return cartRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy cart theo id → trả về DTO
    @Override
    public CartDTO getCartById(int id) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return convertToDTO(cart);
    }

    // Lưu cart entity (trường hợp cần)
    @Override
    public Cart saveCart(Cart cart) {
        return cartRepo.save(cart);
    }

    // Xóa cart theo id
    @Override
    public void deleteCartById(int id) {
        cartRepo.deleteById(id);
    }

    // Thêm product vào giỏ hàng của user
    @Override
    public CartDTO addToCart(int userId, int productId, int quantity) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // tìm hoặc tạo mới cart theo user
        Cart cart = cartRepo.findByUserUserId(user.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });
        // kiểm tra xem product đã có trong cart chưa
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId() == productId)
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // nếu có rồi, chỉ tăng quantity
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // nếu chưa có, thêm mới
            cart.addCartItem(product, quantity);
        }
        // Cập nhật total price
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum());
        cartRepo.save(cart);
        return convertToDTO(cart);
    }

    // Xóa toàn bộ items trong cart theo user
    @Override
    public void deleteAllCartItemsByUserId(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepo.save(cart);
    }

    // Lấy toàn bộ items trong cart theo userId
    @Override
    public List<CartDTO> getCartItemsByUserId(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));
        
        return List.of(convertToDTO(cart));
    }

    // Xóa item trong cart theo itemId
    @Override
    public CartDTO deleteCartItemById(int userId, int productId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found for product id: " + productId));

        cart.getCartItems().remove(item);
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum());
        cartRepo.save(cart);
        return convertToDTO(cart);
    }

    // Cập nhật số lượng item trong cart theo itemId
    @Override
    public CartDTO updateCartItemQuantity(int userId, int productId, int delta) {
        if (delta == 0) {
            throw new IllegalArgumentException("Quantity delta must not be zero");
        }

        Cart cart = cartRepo.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found for product id: " + productId));

        // Cập nhật số lượng
        int newQty = item.getQuantity() + delta;
        if (newQty <= 0) {
            cart.getCartItems().remove(item); // xóa item khỏi cart
        } else {
            item.setQuantity(newQty);
        }

        // Cập nhật total price
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum());

        cartRepo.save(cart);

        // trả về 1 CartDTO duy nhất (không cần List)
        return convertToDTO(cart);
    }

    // Helper: convert Cart Entity -> CartDTO
    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUser().getUserId());
        dto.setTotalPrice(cart.getTotalPrice()); // tính tổng giá
        dto.setCartItems(cart.getCartItems()
                .stream()
                .map(item -> new ItemDTO(
                        item.getProduct().getProductId(),
                        item.getProduct().getProductName(), // include product name in DTO
                        item.getProduct().getImageUrl(), // include image URL in DTO
                        item.getQuantity(),
                        item.getProduct().getPrice() // include price in DTO
                ))
                .collect(Collectors.toList()));
        return dto;
    }

    // Helper: convert CartDTO -> Cart Entity
    private Cart convertToEntity(CartDTO dto) {
        Cart cart = new Cart();
        cart.setCartId(dto.getCartId());
        cart.setUser(userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        cart.setTotalPrice(dto.getTotalPrice());
        for (ItemDTO itemDTO : dto.getCartItems()) {
            // Giả sử bạn có một phương thức để tìm Product theo ID
            Product product = productRepo.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            cart.addCartItem(product, itemDTO.getQuantity());
        }
        return cart;
    }

}