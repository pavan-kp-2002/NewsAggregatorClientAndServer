package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.*;

public class CategoryConfigurationService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/category-configurations";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void getAllCategoryConfigurations() {
        String token = TokenStore.getToken();
        if (token == null || token.isEmpty()) {
            System.out.println("User is not authenticated. Please log in first.");
            return;
        }
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
                JsonNode array = objectMapper.readTree(responseBody);
                for (JsonNode node : array) {
                    String name = node.path("categoryName").asText();
                    String status = node.path("status").asText();
                    System.out.println("Category: " + name + ", -Status: " + status);
                }
            } else {
                System.out.println("Failed to fetch categories: " + responseBody);
            }
        } catch (Exception e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
    }

    public void editCategoryConfiguration(String categoryName) {
        String token = TokenStore.getToken();
        if (token == null || token.isEmpty()) {
            System.out.println("User is not authenticated. Please log in first.");
            return;
        }
        Request request = new Request.Builder()
                .url(BASE_URL + "/edit")
                .addHeader("Authorization", "Bearer " + token)
                .post(RequestBody.create(categoryName, MediaType.parse("application/json")))
                .build();
        try (Response response = client.newCall(request).execute()) {
            String message = response.body().string();
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("Error editing category: " + e.getMessage());
        }
    }

}
