package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.ReportNotificationDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.List;

public class ReportNotificationService {
    private static final String BASE_URL = "/api/v1/admin/report-notification";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public ReportNotificationService(){
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    public void viewUnreadReportedArticlesNotifications(){
        try {
            String url = BASE_URL + "/new";
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()){
                if(!response.isSuccessful()){
                    System.out.println("Failed to fetch notifications: " + response.message());
                }else {
                String responseBody = response.body().string();
                List<ReportNotificationDTO> reportNotificationDTOList = objectMapper.readValue(
                        responseBody,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ReportNotificationDTO.class)
                );
                printReportNotificationDTOList(reportNotificationDTOList);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching reported articles notifications: " + e.getMessage());
        }
    }

    public void viewAllReportedArticlesNotifications(){
        try {
            String url = BASE_URL + "/all";
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()){
                if(!response.isSuccessful()){
                    System.out.println("Failed to fetch notifications: " + response.message());
                }else {
                    String responseBody = response.body().string();
                    List<ReportNotificationDTO> reportNotificationDTOList = objectMapper.readValue(
                            responseBody,
                            objectMapper.getTypeFactory().constructCollectionType(List.class, ReportNotificationDTO.class)
                    );
                    printReportNotificationDTOList(reportNotificationDTOList);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching reported articles notifications: " + e.getMessage());
        }
    }

    public void printReportNotificationDTOList(List<ReportNotificationDTO> reportNotificationDTOList) {
        if (reportNotificationDTOList == null || reportNotificationDTOList.isEmpty()) {
            System.out.println("No reported articles notifications found.");
            return;
        }
        System.out.println("R E P O R T E D   A R T I C L E S   N O T I F I C A T I O N S");
        for (ReportNotificationDTO notification : reportNotificationDTOList) {
            System.out.println(notification.getMessage());
        }
    }
}
