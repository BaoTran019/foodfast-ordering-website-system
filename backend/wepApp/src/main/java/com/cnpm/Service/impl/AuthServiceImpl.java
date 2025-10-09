package com.cnpm.Service.impl;

import org.springframework.stereotype.Service;
import com.cnpm.Service.AuthService;
import com.cnpm.Entity.User;
import com.cnpm.Service.UserService;
import com.cnpm.DTO.LoginRequest;
import com.cnpm.DTO.RegisterRequest;
import com.cnpm.DTO.AuthResponse;
import com.cnpm.Security.TokenProvider;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    public AuthServiceImpl(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
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
        String token = tokenProvider.generateToken(user.getName());
        return new AuthResponse(user.getId(), token, "Login successful", user.getRole());
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        User existingUser = userService.getUserByName(registerRequest.getName());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setAddress(registerRequest.getAddress());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setRole(registerRequest.getRole());

        userService.saveUser(newUser);

        User savedUser = userService.getUserByPhone(newUser.getPhone());
        String token = tokenProvider.generateToken(newUser.getName());
        return new AuthResponse(savedUser.getId(), token, "User registered successfully", savedUser.getRole());
    }
}
