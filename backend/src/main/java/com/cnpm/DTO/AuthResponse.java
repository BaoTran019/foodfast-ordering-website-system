package com.cnpm.DTO;

import lombok.Data;

@Data
public class AuthResponse {
    private int userId;
    private String token;
    private String message;
    private String role;

    public AuthResponse(int userId, String token, String message, String role) {
        this.userId = userId;
        this.token = token;
        this.message = message;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

