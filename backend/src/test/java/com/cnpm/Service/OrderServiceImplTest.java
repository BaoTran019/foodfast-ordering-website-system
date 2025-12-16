package com.cnpm.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager; // Hoặc javax.persistence tùy version Spring Boot

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cnpm.Entity.Cart;
import com.cnpm.Entity.Order;
import com.cnpm.Entity.Product;
import com.cnpm.Entity.User;
import com.cnpm.Entity.Payment;
import com.cnpm.Entity.CartItem;
import com.cnpm.Repository.CartRepo;
import com.cnpm.Repository.OrderRepo;
import com.cnpm.Repository.PaymentRepo;
import com.cnpm.Service.impl.OrderServiceImpl;
import com.cnpm.DTO.OrderDTO;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private EntityManager entityManager; // Mock EntityManager

    // Dữ liệu mẫu
    private User mockUser;
    private Cart mockCart;
    private Product mockProduct;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUserId(1);

        mockProduct = new Product();
        mockProduct.setProductId(100);
        mockProduct.setPrice(100000.0); // 100k

        mockCart = new Cart();
        mockCart.setCartId(500);
        mockCart.setUser(mockUser);
        
        // Tạo CartItem
        CartItem item = new CartItem();
        item.setProduct(mockProduct);
        item.setQuantity(2); // Mua 2 cái
        
        List<CartItem> items = new ArrayList<>();
        items.add(item);
        mockCart.setCartItems(items);
    }

    // =================================================================
    // CASE 1: Đặt hàng thành công (Happy Path)
    // =================================================================
    @Test
    void createOrderFromCart_WhenInfoValid_ShouldCreateOrderAndPayment() {
        // 1. ARRANGE
        int userId = 1;
        String paymentMethod = "COD";
        String recipientName = "Nguyen Van A";
        String recipientPhone = "0901234567";
        String address = "123 Hanoi";

        // Mock tìm thấy giỏ hàng
        when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.of(mockCart));

        // Mock lưu Order: Trả về chính order đó kèm ID giả
        when(orderRepo.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setOrderId(888); // Giả lập ID DB sinh ra
            return savedOrder;
        });

        // Mock entityManager (refresh là hàm void, dùng doNothing)
        doNothing().when(entityManager).refresh(any(Order.class));

        // 2. ACT
        // Giả sử hàm convertToDTO bạn tự xử lý
        OrderDTO result = orderService.createOrderFromCart(userId, paymentMethod, recipientName, recipientPhone, address);

        // 3. ASSERT
        assertEquals(200000.0, result.getTotalPrice()); // (Lưu ý: kiểm tra getter trong DTO hoặc capture argument save)

        // b. Kiểm tra các thông tin Order được set đúng chưa
        verify(orderRepo).save(argThat(order -> 
            order.getStatus().equals("Pending") &&
            order.getRecipientName().equals("Nguyen Van A") &&
            order.getDeliveryAddress().equals("123 Hanoi")
        ));

        // c. Kiểm tra Payment được tạo
        verify(paymentRepo).save(argThat(payment -> 
            payment.getOrderId() == 888 && // ID lấy từ order đã save
            payment.getStatus().equals("UNPAID") &&
            payment.getAmount()getAmount() == 200000.0
        ));

        // d. Kiểm tra Giỏ hàng đã bị xóa chưa
        assertTrue(mockCart.getCartItems().isEmpty());
        assertEquals(0, mockCart.getTotalPrice());
        verify(cartRepo).save(mockCart);
        
        // e. Kiểm tra EntityManager có refresh order không
        verify(entityManager).refresh(any(Order.class));
    }

    // =================================================================
    // CASE 2: Đặt hàng thiếu thông tin (Tên hoặc Địa chỉ null)
    // =================================================================
    @Test
    void createOrderFromCart_WhenMissingRecipientInfo_ShouldThrowException() {
        // LƯU Ý: Code gốc của bạn CHƯA kiểm tra null/rỗng.
        // Test case này giả định bạn CÓ THÊM logic validation (xem phần giải thích bên dưới).
        
        // 1. ARRANGE
        int userId = 1;
        //when(cartRepo.findByUserUserId(userId)).thenReturn(Optional.of(mockCart));

        // 2. ACT & ASSERT
        // Trường hợp tên người nhận bị null
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrderFromCart(userId, "COD", null, "090123", "Hanoi");
        });
        assertEquals("Recipient name is required", e1.getMessage());

        // Trường hợp địa chỉ bị Empty
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrderFromCart(userId, "COD", "Name", "090123", "");
        });
        assertEquals("Shipping address is required", e2.getMessage());

        // Đảm bảo không lưu Order nếu validation fail
        verify(orderRepo, never()).save(any());
    }
}
