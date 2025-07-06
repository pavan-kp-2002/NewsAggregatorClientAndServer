package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.User;

import java.util.List;

public interface ArticleModerationService {
    void reportArticle(Long articleId);
    void hideArticle(Long articleId);
    void unhideArticle(Long articleId);
    void hideCategory(Long categoryId);
    void unhideCategory(Long categoryId);
    void addBlockedKeyword(String keyword);
    void removeBlockedKeyword(String keyword);
}
