package com.learnandcode.news_aggregation_client.dto;

public class LoginRequestDTO {
    public String email;
    public String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
