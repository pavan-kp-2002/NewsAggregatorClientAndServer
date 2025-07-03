package com.learnandcode.news_aggregation_client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ArticleDTO {
    private Long articleId;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String fetchedAt;
    private CategoryDTO categoryId;

    public CategoryDTO getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryDTO categoryId) {
        this.categoryId = categoryId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(String fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

}
