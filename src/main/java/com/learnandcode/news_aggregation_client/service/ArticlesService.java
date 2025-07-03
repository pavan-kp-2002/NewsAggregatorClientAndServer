package com.learnandcode.news_aggregation_client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregation_client.dto.ArticleDTO;
import com.learnandcode.news_aggregation_client.dto.SavedArticleDTO;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Collections;
import java.util.List;

public class ArticlesService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/saved-articles";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public ArticlesService() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<SavedArticleDTO> showSavedArticles() {
        try {
            String token = TokenStore.getToken();
            if (token == null || token.isEmpty()) {
                System.out.println("User is not authenticated. Please log in first.");
                return Collections.emptyList();
            }
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Failed to fetch saved articles: " + response.message());
                    return Collections.emptyList();
                }
                String responseBody = response.body().string();
                return objectMapper.readValue(
                        responseBody,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, SavedArticleDTO.class));
            }
        } catch (Exception e) {
            System.out.println("Error fetching saved articles: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public String saveArticle(Long articleId) {
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                return  "User is not authenticated. Please log in first.";
            }
            RequestBody body = RequestBody.create(new byte[0], null);
            Request request = new Request.Builder()
                    .url(BASE_URL + "/" + articleId)
                    .addHeader("Authorization", "Bearer " + token)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return "Failed to save article: " + response.message();
                }
                return response.body().string();
            }
        }catch (Exception e) {
            return "Error saving article: " + e.getMessage();
        }
    }

    public String deleteArticle(Long articleId) {
        try {
            String token = TokenStore.getToken();
            if(token == null || token.isEmpty()) {
                return "User is not authenticated. Please log in first.";
            }
            Request request = new Request.Builder()
                    .url(BASE_URL + "/" + articleId)
                    .addHeader("Authorization", "Bearer " + token)
                    .delete()
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return "Failed to delete article: " + response.message();
                }
                return response.body().string();
            }
        } catch (Exception e) {
            return "Error deleting article: " + e.getMessage();
        }
    }

    public void printArticles(List<SavedArticleDTO> articles, String title) {
        if (articles == null || articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }
        System.out.println(title);
        for (SavedArticleDTO article : articles) {
            System.out.println("Articled Id: " + article.getArticle().getArticleId());
            System.out.println(article.getArticle().getTitle());
            System.out.println(article.getArticle().getDescription());
            System.out.println("URL:\n" + article.getArticle().getUrl());
            System.out.println("Category: " + article.getArticle().getCategoryId().getName());
            System.out.println();
        }
        System.out.println("1. Back");
        System.out.println("2. Logout");
        System.out.println("3. Delete Article");
    }
}
