package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.CategoryDTO;
import com.learnandcode.news_aggregation_client.dto.ExternalServerDTO;
import com.learnandcode.news_aggregation_client.dto.ExternalServerDetailDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/servers";
    private  final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper;

    public AdminService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    public void viewExternalServersList() {
        try{
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            Request httpRequest = new Request.Builder()
                    .url(BASE_URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try(Response response = client.newCall(httpRequest).execute()){
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch external servers list: " + response.message());
                    return;
                }
                String responseBody = response.body().string();

                List<ExternalServerDTO> servers = objectMapper.readValue(
                        responseBody,
                        new TypeReference<List<ExternalServerDTO>>() {}
                );
                System.out.println("E X T E R N A L   S E R V E R S   L I S T");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                int i = 1;
                for (ExternalServerDTO server : servers) {
                    System.out.printf("%d. Server: %s | Status: %s | Last Accessed: %s\n",
                            i++,
                            server.getServerName(),
                            server.getStatus(),
                            formatter.format(server.getLastAccessed())
                    );
                }
            }
        }catch (Exception e){
            System.out.println("Error fetching external servers list: " + e.getMessage());
        }

    }

    public void viewExternalServersDetailsList() {
        try{
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            Request httpRequest = new Request.Builder()
                    .url(BASE_URL+"/details")
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try(Response response = client.newCall(httpRequest).execute()){
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch external servers Details: " + response.message());
                    return;
                }
                String responseBody = response.body().string();

                List<ExternalServerDetailDTO> servers = objectMapper.readValue(
                        responseBody,
                        new TypeReference<List<ExternalServerDetailDTO>>() {}
                );
                System.out.println("E X T E R N A L   S E R V E R S   D E T A I L S");

                for (ExternalServerDetailDTO server : servers) {
                    System.out.printf("%d. Server: %s | Api Key: %s | Api URL: %s\n",
                            server.getServerId(),
                            server.getServerName(),
                            server.getApiKey(),
                            server.getEndPoint()
                    );
                }
            }
        }catch (Exception e){
            System.out.println("Error fetching external servers details list: " + e.getMessage());
        }
    }

    public void updateExternalServerDetails(String serverId, String apiKey) {
        try {
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }

            String json = "{\"apiKey\":\"" + apiKey + "\"}";

            Request httpRequest = new Request.Builder()
                    .url(BASE_URL + "/" + serverId)
                    .addHeader("Authorization", "Bearer " + token)
                    .patch(okhttp3.RequestBody.create(json, okhttp3.MediaType.get("application/json")))
                    .build();

            try (Response response = client.newCall(httpRequest).execute()) {
                String responseBody = response.body().string();
                if (!response.isSuccessful()) {
                    System.out.println("Failed to update external server details: " + response.message());
                    return;
                }
                ExternalServerDetailDTO updatedServer = objectMapper.readValue(responseBody, ExternalServerDetailDTO.class);
                System.out.println("External server details updated successfully.\n New API Key: " + updatedServer.getApiKey());
            }
        } catch (Exception e) {
            System.out.println("Error updating external server details: " + e.getMessage());
        }
    }

    public void viewAllCategories() {
        try {
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            Request httpRequest = new Request.Builder()
                    .url("http://localhost:8080/api/v1/categories")
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try (Response response = client.newCall(httpRequest).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch categories: " + response.message());
                    return;
                }
                String responseBody = response.body().string();
                List<CategoryDTO> categories = objectMapper.readValue(
                        responseBody,
                        new com.fasterxml.jackson.core.type.TypeReference<List<CategoryDTO>>() {}
                );
                System.out.println("C A T E G O R I E S");
                for (CategoryDTO cat : categories) {
                    System.out.printf("%d. %s\n", cat.getId(), cat.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
    }

    // Add a new category
    public void addCategory(String name) {
        try {
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return;
            }
            String json = "{\"name\":\"" + name + "\"}";
            Request httpRequest = new Request.Builder()
                    .url("http://localhost:8080/api/v1/categories")
                    .addHeader("Authorization", "Bearer " + token)
                    .post(okhttp3.RequestBody.create(json, okhttp3.MediaType.get("application/json")))
                    .build();
            try (Response response = client.newCall(httpRequest).execute()) {
                String responseBody = response.body().string();
                JsonNode errorNode = objectMapper.readTree(responseBody);
                if (errorNode.has("message") && response.code() != 201) {
                    String errorMessage = errorNode.get("message").asText();
                    System.out.println("Error adding category: " + errorMessage);
                    return;
                }
                CategoryDTO created = objectMapper.readValue(responseBody, CategoryDTO.class);
                System.out.println("Category created! ID: " + created.getId() + ", Name: " + created.getName());
            }
        } catch (Exception e) {
            System.out.println("Error adding category: " + e.getMessage());
        }
    }
}
