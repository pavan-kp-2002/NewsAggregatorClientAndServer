package com.learnandcode.news_aggregation_client.dto;

public class SavedArticleDTO {
    private Long savedArticleId;
    private ArticleDTO article;

    public Long getSavedArticleId() {
        return savedArticleId;
    }

    public void setSavedArticleId(Long savedArticleId) {
        this.savedArticleId = savedArticleId;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }
}
