package com.cnpm.Service.impl;

import org.springframework.stereotype.Service;
import com.cnpm.Service.AuthService;
import com.cnpm.Service.CartService;
import com.cnpm.Entity.Cart;
import com.cnpm.Entity.User;
import com.cnpm.Service.UserService;
import com.cnpm.DTO.LoginRequest;
import com.cnpm.DTO.OTPResponse;
import com.cnpm.DTO.RegisterRequest;
import com.cnpm.DTO.AuthResponse;
import com.cnpm.Security.TokenProvider;
import java.security.SecureRandom;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CartService cartService;
    private final TokenProvider tokenProvider;

    public AuthServiceImpl(UserService userService, CartService cartService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.cartService = cartService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userService.getUserByPhone(loginRequest.getPhone());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            System.out.println("Login failed for phone: " + loginRequest.getPhone());
            System.out.println("Provided password: " + loginRequest.getPassword());
            throw new RuntimeException("Invalid phone number or password");
        }
        String token = tokenProvider.generateToken(user.getFullName());
        return new AuthResponse(user.getUserId(), token, "Login successful", user.getRole());
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        User existingUser = userService.getUserByName(registerRequest.getUserName());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        User newUser = new User();
        newUser.setFullName(registerRequest.getUserName());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setAddress(registerRequest.getAddress());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setRole(registerRequest.getRole());

        userService.saveUser(newUser);

        Cart cart = new Cart();
        cart.setUser(newUser);
        cart.setTotalPrice(0);
        cartService.saveCart(cart);

        User savedUser = userService.getUserByPhone(newUser.getPhone());
        String token = tokenProvider.generateToken(newUser.getFullName());
        return new AuthResponse(savedUser.getUserId(), token, "User registered successfully", savedUser.getRole());
    }

    @Override
    public OTPResponse forgotPassword(String phoneNumber) {
        User user = userService.getUserByPhone(phoneNumber);
        if (user == null) {
            throw new RuntimeException("Phone number not found");
        }

        SecureRandom secureRandom = new SecureRandom();
        int otp = 100000 + secureRandom.nextInt(900000);
        // TODO: integrate with SMS/email provider to deliver the OTP to the user

        return new OTPResponse(user.getUserId(), String.valueOf(otp), "Password reset instructions sent to your email");
    }

    @Override
    public AuthResponse verifyOtp(String phoneNumber, String otp) {
        // Giả sử mã OTP hợp lệ là "123456" cho mục đích minh họa
        if (!"123456".equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }
        User user = userService.getUserByPhone(phoneNumber);
        if (user == null) {
            throw new RuntimeException("Phone number not found");
        }
        return new AuthResponse(user.getUserId(), null, "OTP verified successfully", user.getRole());
    }

    @Override
    public AuthResponse resetPassword(String phoneNumber, String newPassword) {
        User user = userService.getUserByPhone(phoneNumber);
        if (user == null) {
            throw new RuntimeException("Phone number not found");
        }
        user.setPassword(newPassword);
        userService.saveUser(user);
        return new AuthResponse(user.getUserId(), null, "Password reset successfully", user.getRole());
    }
}
