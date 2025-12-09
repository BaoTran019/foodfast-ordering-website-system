package com.cnpm.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String role = "USER"; // Default role


    public RegisterRequest(String userName, String password, String email, String address, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
