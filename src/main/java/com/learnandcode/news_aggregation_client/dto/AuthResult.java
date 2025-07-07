package com.learnandcode.news_aggregation_client.dto;

public class AuthResult {
    public Boolean success;
    public String message;

    public AuthResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
