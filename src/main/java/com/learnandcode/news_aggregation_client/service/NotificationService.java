package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.NotificationDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.List;

public class NotificationService {

    private static final String BASE_URL = "http://localhost:8080/api/v1/notifications";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public NotificationService() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    public void showNotifications() {
        try {
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
                if(response.code() == 404){
                    System.out.println("No new notifications. Its so quiet here.");
                    return;
                }
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch notifications: " + response.message());
                    return;
                }
                String responseBody = response.body().string();
                List<NotificationDTO> notifications = objectMapper.readValue(
                        responseBody,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationDTO.class)
                );
                if (notifications.isEmpty()) {
                    System.out.println("No notifications found.");
                    return;
                }
                System.out.println("N O T I F I C A T I O N S");
                for (NotificationDTO notification : notifications) {
                    System.out.println("Article Id: " + notification.getArticleId());
                    System.out.println("Title: " + notification.getTitle());
                    System.out.println("Link to Article: " + notification.getLinkToArticle());
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching notifications: " + e.getMessage());
        }
    }

}
