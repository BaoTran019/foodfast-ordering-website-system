package com.cnpm.Service;

import org.springframework.stereotype.Service;

import com.cnpm.DTO.AuthResponse;
import com.cnpm.DTO.LoginRequest;
import com.cnpm.DTO.OTPResponse;
import com.cnpm.DTO.RegisterRequest;

@Service
public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest registerRequest);

    OTPResponse forgotPassword(String phoneNumber);

    AuthResponse verifyOtp(String phoneNumber, String otp);

    AuthResponse resetPassword(String phoneNumber, String newPassword);
}
