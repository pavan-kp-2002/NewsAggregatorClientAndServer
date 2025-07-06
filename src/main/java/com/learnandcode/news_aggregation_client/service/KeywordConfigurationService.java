package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.KeywordConfigurationDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Collections;
import java.util.List;

public class KeywordConfigurationService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/keywords-config";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public KeywordConfigurationService() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    public List<KeywordConfigurationDTO> getUserKeywordConfigurations(){
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return Collections.emptyList();
            }
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()){
                String responseBody = response.body().string();
                if(!response.isSuccessful()){
                    System.out.println("Failed to fetch keyword configurations: " + responseBody);
                    return Collections.emptyList();
                }
                return objectMapper.readValue(
                        responseBody,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, KeywordConfigurationDTO.class));
            }
        }catch (Exception e) {
            System.out.println("Error fetching keyword configurations: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void addKeywordConfiguration(String keyword) {
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            String json = keyword;
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .post(okhttp3.RequestBody.create(json, okhttp3.MediaType.parse("application/json")))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String  responseBody = response.body().string();
                String message = responseBody;
                try {
                    message = objectMapper.readTree(responseBody).path("message").asText(responseBody);
                }catch (Exception ignore){}
                if (!response.isSuccessful()) {
                    System.out.println("Failed to add keyword configuration: " + message);
                } else {
                    System.out.println("Keyword configuration added successfully.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding keyword configuration: " + e.getMessage());
        }
    }

    public void updateKeywordConfiguration(String keyword){
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            String json = keyword;
            Request request = new Request.Builder()
                    .url(BASE_URL + "/edit")
                    .addHeader("Authorization", "Bearer " + token)
                    .post(okhttp3.RequestBody.create(json, okhttp3.MediaType.parse("application/json")))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                String message = responseBody;
                try {
                    message = objectMapper.readTree(responseBody).path("message").asText(responseBody);
                } catch (Exception ignore){}
                if (!response.isSuccessful()) {
                    System.out.println("Failed to update keyword configuration: " + message);
                } else {
                    System.out.println("Keyword configuration updated successfully.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating keyword configuration: " + e.getMessage());
        }
    }
}
