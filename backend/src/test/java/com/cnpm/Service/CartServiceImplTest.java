package com.cnpm.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cnpm.DTO.CartDTO;
import com.cnpm.Entity.Cart;
import com.cnpm.Entity.CartItem;
import com.cnpm.Entity.Product;
import com.cnpm.Entity.User;
import com.cnpm.Repository.CartRepo;
import com.cnpm.Repository.UserRepo;
import com.cnpm.Repository.ProductRepo;
import com.cnpm.Service.impl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService; // Class cần test

    @Mock
    private UserRepo userRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private ProductRepo productRepo;

    // Dữ liệu giả lập dùng chung
    private User mockUser;
    private Product mockProduct;
    private Cart mockCart;
    private CartItem mockCartItem;

    @BeforeEach
    void setUp() {
        // Chuẩn bị dữ liệu mẫu trước mỗi bài test
        mockUser = new User();
        mockUser.setUserId(1);

        mockProduct = new Product();
        mockProduct.setProductId(100);
        mockProduct.setPrice(50000.0); // Giá 50k

        mockCartItem = new CartItem();
        mockCartItem.setProduct(mockProduct);
        mockCartItem.setQuantity(2); // Số lượng 2

        mockCart = new Cart();
        mockCart.setUser(mockUser);
        List<CartItem> items = new ArrayList<>(); // Dùng ArrayList để có thể remove
        items.add(mockCartItem);
        mockCart.setCartItems(items);
        mockCart.setTotalPrice(100000.0); // 2 * 50k
    }

    // ---------------------------------------------------------
    // TEST CASE 1: Xóa sản phẩm khỏi giỏ hàng (Delete)
    // ---------------------------------------------------------

    @Test
    void deleteCartItemById_WhenItemExists_ShouldRemoveItemAndRecalculatePrice() {
        // 1. ARRANGE
        int userId = 1;
        int productId = 100;

        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));
        when(cartRepo.findByUser(mockUser)).thenReturn(Optional.of(mockCart));
        
        // Mock hành vi save để không bị lỗi NullPointerException nếu service có dùng kết quả trả về
        when(cartRepo.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. ACT
        CartDTO result = cartService.deleteCartItemById(userId, productId);

        // 3. ASSERT
        // List item phải rỗng sau khi xóa
        assertTrue(mockCart.getCartItems().isEmpty());
        // Tổng tiền phải về 0
        assertEquals(0.0, mockCart.getTotalPrice());
        // Verify xem hàm save đã được gọi chưa
        verify(cartRepo).save(mockCart);
    }

    // @Test
    // void deleteCartItemById_WhenUserNotFound_ShouldThrowException() {
    //     // 1. ARRANGE
    //     when(userRepo.findById(99)).thenReturn(Optional.empty());

    //     // 2. ACT & ASSERT
    //     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    //         cartService.deleteCartItemById(99, 100);
    //     });

    //     assertEquals("User not found", exception.getMessage());
    // }

    // ---------------------------------------------------------
    // TEST CASE 2: Cập nhật số lượng sản phẩm (Update)
    // ---------------------------------------------------------

    @Test
    void updateCartItemQuantity_IncreaseQuantity_ShouldUpdatePrice() {
        // 1. ARRANGE
        int userId = 1;
        int productId = 100;
        int delta = 1; // Tăng thêm 1

        // Lưu ý: Code update của bạn dùng findByUserUserId chứ không phải findByUser
        when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.of(mockCart));
        when(cartRepo.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        // 2. ACT
        CartDTO result = cartService.updateCartItemQuantity(userId, productId, delta);

        // 3. ASSERT
        // Số lượng cũ 2 + 1 = 3
        assertEquals(3, mockCartItem.getQuantity());
        // Tổng tiền mới: 3 * 50.000 = 150.000
        assertEquals(150000.0, mockCart.getTotalPrice());
        verify(cartRepo).save(mockCart);
    }

    // @Test
    // void updateCartItemQuantity_DecreaseQuantityToZero_ShouldRemoveItem() {
    //     // 1. ARRANGE
    //     int userId = 1;
    //     int productId = 100;
    //     int delta = -2; // Giảm 2 (Số lượng hiện tại là 2 -> Về 0)

    //     when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.of(mockCart));
    //     when(cartRepo.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     cartService.updateCartItemQuantity(userId, productId, delta);

    //     // 3. ASSERT
    //     // Item phải bị xóa khỏi list
    //     assertTrue(mockCart.getCartItems().isEmpty());
    //     // Tổng tiền về 0
    //     assertEquals(0.0, mockCart.getTotalPrice());
    // }

    // @Test
    // void updateCartItemQuantity_WhenDeltaIsZero_ShouldThrowException() {
    //     // 1. ARRANGE
    //     int delta = 0;

    //     // 2. ACT & ASSERT
    //     IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    //         cartService.updateCartItemQuantity(1, 100, delta);
    //     });

    //     assertEquals("Quantity delta must not be zero", exception.getMessage());
    //     // Đảm bảo không gọi xuống DB
    //     verify(cartRepo, never()).findByUserUserId(anyInt());
    // }

    // // =================================================================
    // // CASE 1: Giỏ hàng đã có, Sản phẩm MỚI (chưa có trong giỏ)
    // // =================================================================
    // @Test
    // void addToCart_WhenCartExists_AndItemIsNew_ShouldAddNewItem() {
    //     // 1. ARRANGE
    //     int userId = 1;
    //     int productId = 100;
    //     int quantity = 2;

    //     when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));
    //     when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));
    //     when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.of(mockCart));
        
    //     // Mock save cart
    //     when(cartRepo.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     // Giả sử hàm convertToDTO bạn xử lý ok hoặc mock riêng
    //     cartService.addToCart(userId, productId, quantity);

    //     // 3. ASSERT
    //     // List phải có 1 phần tử
    //     assertEquals(1, mockCart.getCartItems().size());
        
    //     CartItem newItem = mockCart.getCartItems().get(0);
    //     assertEquals(productId, newItem.getProduct().getProductId());
    //     assertEquals(2, newItem.getQuantity());

    //     // Kiểm tra tính tiền: 1000 * 2 * 1.0 = 2000
    //     assertEquals(2000.0, mockCart.getTotalPrice());
        
    //     verify(cartRepo).save(mockCart);
    // }

    // // =================================================================
    // // CASE 2: Giỏ hàng đã có, Sản phẩm ĐÃ CÓ trong giỏ (Cộng dồn số lượng)
    // // =================================================================
    // @Test
    // void addToCart_WhenCartExists_AndItemExists_ShouldUpdateQuantity() {
    //     // 1. ARRANGE
    //     int userId = 1;
    //     int productId = 100;
    //     int addQuantity = 3;

    //     // Setup sẵn 1 item trong giỏ
    //     CartItem existingItem = new CartItem();
    //     existingItem.setProduct(mockProduct);
    //     existingItem.setQuantity(2); // Đang có 2 cái
    //     mockCart.getCartItems().add(existingItem);

    //     when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));
    //     when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));
    //     when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.of(mockCart));
    //     when(cartRepo.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     cartService.addToCart(userId, productId, addQuantity);

    //     // 3. ASSERT
    //     // List vẫn chỉ có 1 phần tử (không được tạo dòng mới)
    //     assertEquals(1, mockCart.getCartItems().size());
        
    //     CartItem item = mockCart.getCartItems().get(0);
    //     // Số lượng mới: 2 (cũ) + 3 (mới) = 5
    //     assertEquals(5, item.getQuantity());

    //     // Kiểm tra tính tiền: 1000 * 5 * 1.0 = 5000
    //     assertEquals(5000.0, mockCart.getTotalPrice());
    // }

    // =================================================================
    // CASE 3: User chưa có giỏ hàng (Tạo Cart mới)
    // =================================================================
    // @Test
    // void addToCart_WhenCartDoesNotExist_ShouldCreateNewCart() {
    //     // 1. ARRANGE
    //     int userId = 1;
    //     int productId = 100;

    //     when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));
    //     when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));
        
    //     // Mock tìm cart trả về rỗng -> Kích hoạt nhánh orElseGet
    //     when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.empty());

    //     // Mock hành động save cart mới
    //     // Lưu ý: Code của bạn gọi save 2 lần (1 lần tạo mới, 1 lần sau khi tính tiền)
    //     when(cartRepo.save(any(Cart.class))).thenAnswer(i -> {
    //         Cart c = i.getArgument(0);
    //         if (c.getCartItems() == null) c.setCartItems(new ArrayList<>()); // Đảm bảo list không null khi khởi tạo
    //         return c;
    //     });

    //     // 2. ACT
    //     cartService.addToCart(userId, productId, 1);

    //     // 3. ASSERT
    //     // Verify cartRepo.save phải được gọi ít nhất 1 lần (hoặc 2 lần tùy logic code)
    //     verify(cartRepo, atLeastOnce()).save(any(Cart.class));
    // }

    // =================================================================
    // CASE 4: Các trường hợp ngoại lệ (Exception)
    // =================================================================
    // @Test
    // void addToCart_WhenUserNotFound_ShouldThrowException() {
    //     when(userRepo.findById(99)).thenReturn(Optional.empty());

    //     Exception e = assertThrows(RuntimeException.class, () -> 
    //         cartService.addToCart(99, 100, 1)
    //     );
    //     assertEquals("User not found", e.getMessage());
    // }

    @Test
    void addToCart_WhenProductNotFound_ShouldThrowException() {
        when(userRepo.findById(1)).thenReturn(Optional.of(mockUser));
        when(productRepo.findById(99)).thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () -> 
            cartService.addToCart(1, 99, 1)
        );
        assertEquals("Product not found", e.getMessage());
    }
}