package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.AuthResult;
import com.learnandcode.news_aggregation_client.dto.LoginRequestDTO;
import com.learnandcode.news_aggregation_client.dto.SignupRequestDTO;
import com.learnandcode.news_aggregation_client.dto.SignupResponseDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.*;

import java.util.Base64;

public class AuthService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/users";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String jwtToken;

    public AuthResult signUp(SignupRequestDTO request) {
        try{
            String json = objectMapper.writeValueAsString(request);

            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request httpRequest = new Request.Builder()
                    .url(BASE_URL + "/sign-up")
                    .post(body)
                    .build();

            try (Response response = client.newCall(httpRequest).execute()) {
                String responseBody = response.body().string();
                if (!response.isSuccessful()) {
                    return new AuthResult(false,responseBody);
                }
                SignupResponseDTO resp = objectMapper.readValue(responseBody, SignupResponseDTO.class);
                return new AuthResult(true, "Sign-up successful! Message: " + resp.message + ", User Name" +resp.username);
                }
        }catch (Exception e) {
            return new AuthResult(false, "Sign-up failed: " + e.getMessage());
        }
    }

    public AuthResult login(LoginRequestDTO request) {
        try {
            String json = objectMapper.writeValueAsString(request);

            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request httpRequest = new Request.Builder()
                    .url(BASE_URL + "/login")
                    .post(body)
                    .build();

            try (Response response = client.newCall(httpRequest).execute()) {
                if (!response.isSuccessful()) {
                    return new AuthResult(false, response.body().string());
                }
                this.jwtToken = response.body().string();
                TokenStore.setToken(this.jwtToken);
                String[] parts = jwtToken.split("\\.");
                if (parts.length == 3) {
                    String payloadJson = new String(Base64.getDecoder().decode(parts[1]));
                    JsonNode payloadNode = objectMapper.readTree(payloadJson);
                    String role = payloadNode.get("role").asText();
                    TokenStore.setRole(role);
                }
                return new AuthResult(true, "Login Successful!");
            }
        }catch (Exception e) {
            return new AuthResult(false, "Login failed: " + e.getMessage());
        }
    }
}
