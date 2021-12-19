package com.uoc.ease_care_backend.dto;

public class LoginRequest {

    private String mobileNumberOrEmail;

    private String password;

    public String getMobileNumberOrEmail() {
        return mobileNumberOrEmail;
    }

    public void setMobileNumberOrEmail(String mobileNumberOrEmail) {
        this.mobileNumberOrEmail = mobileNumberOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}