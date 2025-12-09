package com.cnpm.DTO;

import lombok.Data;

@Data
public class OTPResponse {
    private int userId;
    private String otp;
    private String message;

    public OTPResponse(int userId, String otp, String message) {
        this.userId = userId;
        this.otp = otp;
        this.message = message;
    }

}
