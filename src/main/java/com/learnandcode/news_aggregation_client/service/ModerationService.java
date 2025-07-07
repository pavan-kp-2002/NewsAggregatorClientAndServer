package com.learnandcode.news_aggregation_client.service;

import com.learnandcode.news_aggregation_client.util.TokenStore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModerationService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/admin/moderation";
    private final OkHttpClient client;

    public ModerationService(){
        this.client = new OkHttpClient();
    }

    public void hideArticle(Long articleId) {
        callPost(BASE_URL + "/hide/article/" + articleId, null, "Article hidden");
    }

    public void unhideArticle(Long articleId) {
        callPost(BASE_URL + "/unhide/article/" + articleId, null, "Article unhidden");
    }

    public void hideCategory(Long categoryId) {
        callPost(BASE_URL + "/hide/category/" + categoryId, null, "Category hidden");
    }

    public void unhideCategory(Long categoryId) {
        callPost(BASE_URL + "/unhide/category/" + categoryId, null, "Category unhidden");
    }

    public void blockKeyword(String keyword) {
        callPost(BASE_URL + "/block-keyword?keyword=" + keyword, null, "Keyword blocked");
    }

    public void unblockKeyword(String keyword) {
        callDelete(BASE_URL + "/block-keyword?keyword=" + keyword, "Keyword unblocked");
    }


    private void callPost(String url, RequestBody body, String successMsg) {
        String token = TokenStore.getToken();
        if (token == null || token.isEmpty()) {
            System.out.println("User is not authenticated. Please log in first.");
            return;
        }
        if (body == null) {
            body = RequestBody.create(new byte[0], null);
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Failed: " + response.message());
            } else {
                System.out.println(successMsg + " successfully.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void callDelete(String url, String successMsg) {
        String token = TokenStore.getToken();
        if (token == null || token.isEmpty()) {
            System.out.println("User is not authenticated. Please log in first.");
            return;
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Failed: " + response.message());
            } else {
                System.out.println(successMsg + " successfully.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

