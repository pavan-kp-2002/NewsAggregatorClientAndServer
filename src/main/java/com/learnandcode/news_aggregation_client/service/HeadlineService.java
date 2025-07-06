package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.ArticleDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Collections;
import java.util.List;

public class HeadlineService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/articles";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ArticleDTO> getArticlesByDateRangeAndCategory(String startDate, String endDate, int categoryId) {
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return Collections.emptyList();
            }

            String url = BASE_URL + "/date-range";
            String jsonBody = String.format("{\"startDate\":\"%s\",\"endDate\":\"%s\",\"categoryId\":\"%s\"}", startDate, endDate, categoryId);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .post(RequestBody.create(jsonBody, okhttp3.MediaType.get("application/json")))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch articles by date range: " + response.message());
                    return Collections.emptyList();
                }
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, ArticleDTO.class));
            }
        }catch (Exception e){
            System.out.println("Error fetching articles by date range: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<ArticleDTO> getTodaysArticles() {
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return Collections.emptyList();
            }
            String url = BASE_URL + "/today";

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch today's articles: " + response.message());
                    return Collections.emptyList();
                    }
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, ArticleDTO.class));
                }
        }catch (Exception e){
            System.out.println("Error fetching today's articles: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<ArticleDTO> searchArticles(String searchTerm){
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return Collections.emptyList();
            }
            String url = BASE_URL + "/search/" + searchTerm;

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to search articles: " + response.message());
                    return Collections.emptyList();
                }
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, ArticleDTO.class));
            }
        }catch (Exception e) {
            System.out.println("Error searching articles: " + e.getMessage());
            return Collections.emptyList();
        }
    }


    public boolean reactToArticle(Long articleId, String reactionType) {
        try {
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return false;
            }
            String url = "http://localhost:8080/api/v1/article-reactions/" + articleId + "/react?reactionType=" + reactionType;

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .post(RequestBody.create(new byte[0], null))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to react to article: " + response.message());
                    return false;
                }
                System.out.println("Reaction recorded successfully.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error reacting to article: " + e.getMessage());
            return false;
        }
    }

    public void printArticles(List<ArticleDTO> articles, String title) {
        if (articles == null || articles.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }

        System.out.println(title);
        for (ArticleDTO article : articles) {
            System.out.println("Article Id: " + article.getArticleId());
            System.out.println(article.getTitle());
            System.out.println(article.getDescription());
            System.out.println("URL:\n" + article.getUrl());
            System.out.println("Category: " + article.getCategoryId().getName());
            System.out.println();
        }

        System.out.println("1. Back");
        System.out.println("2. Logout");
        System.out.println("3. Save Article");
        System.out.println("4. Like Article");
        System.out.println("5. Dislike Article");
    }
}
