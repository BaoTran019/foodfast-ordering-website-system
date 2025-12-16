package com.cnpm.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cnpm.Service.CartService;
import com.cnpm.Entity.Cart;
import com.cnpm.Entity.User;
import com.cnpm.Service.UserService;
import com.cnpm.Service.AuthService;
import com.cnpm.Service.impl.AuthServiceImpl;
import com.cnpm.DTO.LoginRequest;
import com.cnpm.DTO.OTPResponse;
import com.cnpm.DTO.RegisterRequest;
import com.cnpm.DTO.AuthResponse;
import com.cnpm.Security.TokenProvider;
import com.cnpm.DTO.RegisterRequest;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock 
    private UserService userService;

    @Mock
    private CartService cartService; 

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    // -----------------------------------------------------------
    // TEST CASE 1: Đăng nhập thành công
    // -----------------------------------------------------------
    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsAreCorrect() {
        // 1. ARRANGE (Chuẩn bị dữ liệu giả)
        String phone = "0901111111";
        String password = "pass1";
        String fullName = "Nguyen Van A";
        String fakeToken = "abc.xyz.token";
        int userId = 1;
        String role = "ADMIN";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone(phone);
        loginRequest.setPassword(password);

        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setFullName(fullName);
        mockUser.setPassword(password); // Password trong DB khớp với request
        mockUser.setRole(role);

        // Dạy Mockito hành vi:
        when(userService.getUserByPhone(phone)).thenReturn(mockUser);
        when(tokenProvider.generateToken(fullName)).thenReturn(fakeToken);

        // 2. ACT (Chạy hàm cần test)
        AuthResponse response = authService.login(loginRequest);

        // 3. ASSERT (Kiểm tra kết quả)
        assertNotNull(response);
        assertEquals(fakeToken, response.getToken());
        assertEquals("Login successful", response.getMessage());
        assertEquals(userId, response.getUserId());
        
        // Verify: Đảm bảo tokenProvider thực sự đã được gọi 1 lần
        verify(tokenProvider, times(1)).generateToken(fullName);
    }


    // -----------------------------------------------------------
    // TEST CASE 2: Sai số điện thoại (User trả về null)
    // -----------------------------------------------------------
    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        // 1. ARRANGE
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("0999999999"); // Số lạ
        loginRequest.setPassword("anyPassword");

        // Dạy Mockito: Tìm không thấy user -> trả về null
        when(userService.getUserByPhone("0999999999")).thenReturn(null);

        // 2. & 3. ACT & ASSERT
        // Mong đợi một RuntimeException được ném ra
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Invalid phone number or password", exception.getMessage());
        
        // Verify: Đảm bảo KHÔNG bao giờ gọi hàm tạo token nếu login lỗi
        verify(tokenProvider, never()).generateToken(anyString());
    }

    // -----------------------------------------------------------
    // TEST CASE 3: Sai mật khẩu
    // -----------------------------------------------------------
    @Test
    void login_ShouldThrowException_WhenPasswordIsWrong() {
        // 1. ARRANGE
        String phone = "0909123456";
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone(phone);
        loginRequest.setPassword("wrongPassword"); // Mật khẩu sai

        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setPassword("correctPassword"); // Mật khẩu đúng trong DB

        // Dạy Mockito: Tìm thấy user nhưng password không khớp
        when(userService.getUserByPhone(phone)).thenReturn(mockUser);

        // 2. & 3. ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Invalid phone number or password", exception.getMessage());
        verify(tokenProvider, never()).generateToken(anyString());
    }

    // ---------------------------------------------------------
    // TEST CASE 1: Đăng ký thành công (Happy Case)
    // ---------------------------------------------------------
    @Test
    void register_WhenUsernameValid_ShouldSaveUserAndCartAndReturnToken() {
        // 1. ARRANGE (Chuẩn bị dữ liệu)
        RegisterRequest request = new RegisterRequest();
        request.setUserName("nguyena"); // Lưu ý: code của bạn map userName vào fullName
        request.setPassword("password123");
        request.setEmail("a@email.com");
        request.setPhone("0909999999");
        request.setAddress("Hanoi");
        request.setRole("USER");

        // Giả lập user chưa tồn tại (trả về null)
        when(userService.getUserByName("nguyena")).thenReturn(null);

        // Giả lập sau khi save xong, gọi hàm getUserByPhone sẽ trả về user ĐÃ CÓ ID
        User savedUserFromDB = new User();
        savedUserFromDB.setUserId(101); // ID giả lập do DB sinh ra
        savedUserFromDB.setFullName("nguyena");
        savedUserFromDB.setPhone("0909999999");
        savedUserFromDB.setRole("USER");

        when(userService.getUserByPhone("0909999999")).thenReturn(savedUserFromDB);

        // Giả lập token provider
        when(tokenProvider.generateToken("nguyena")).thenReturn("fake-jwt-token");

        // 2. ACT (Thực hiện)
        AuthResponse response = authService.register(request);

        // 3. ASSERT (Kiểm tra kết quả trả về)
        assertNotNull(response);
        assertEquals(101, response.getUserId()); // ID phải khớp với ID giả lập
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals("User registered successfully", response.getMessage());
        assertEquals("USER", response.getRole());

        // 4. VERIFY (Kiểm tra các hành động phụ)
        // Kiểm tra xem user đã được lưu chưa
        verify(userService).saveUser(argThat(u -> 
            u.getFullName().equals("nguyena") && 
            u.getEmail().equals("a@email.com")
        ));

        // Kiểm tra xem Cart đã được tạo cho user chưa
        verify(cartService).saveCart(argThat(cart -> 
            cart.getUser().getFullName().equals("nguyena") && 
            cart.getTotalPrice() == 0
        ));
    }

    // ---------------------------------------------------------
    // TEST CASE 2: Đăng ký thất bại (Username đã tồn tại)
    // ---------------------------------------------------------
    @Test
    void register_WhenUsernameExists_ShouldThrowException() {
        // 1. ARRANGE
        RegisterRequest request = new RegisterRequest();
        request.setUserName("existingUser");

        // Giả lập user ĐÃ tồn tại (trả về một object User bất kỳ)
        when(userService.getUserByName("existingUser")).thenReturn(new User());

        // 2. ACT & ASSERT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });

        assertEquals("Username already exists", exception.getMessage());

        // 3. VERIFY
        // Đảm bảo không có lệnh save nào được thực thi
        verify(userService, never()).saveUser(any(User.class));
        verify(cartService, never()).saveCart(any(Cart.class));
        verify(tokenProvider, never()).generateToken(anyString());
    }
}
