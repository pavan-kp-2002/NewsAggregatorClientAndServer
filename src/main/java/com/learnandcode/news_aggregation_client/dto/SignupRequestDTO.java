package com.learnandcode.news_aggregation_client.dto;

public class SignupRequestDTO {
    public String email;
    public String password;
    public String username;

    public SignupRequestDTO( String username,String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
