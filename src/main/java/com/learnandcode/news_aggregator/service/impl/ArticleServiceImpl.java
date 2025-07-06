package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.ArticleDateRangeAndCategoryDTO;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.repositories.BlockedKeywordRepository;
import com.learnandcode.news_aggregator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private BlockedKeywordRepository blockedKeywordRepository;

    private Set<String> getBlockedKeywords() {
        return blockedKeywordRepository.findAll()
                .stream()
                .map(bk -> bk.getKeyword())
                .collect(Collectors.toSet());
    }

    @Override
    public List<Article> getTodaysArticles() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);
        List<Article> articles = articleRepository.findVisibleByPublishedAtBetween(startOfDay, endOfDay);
        return filterBlockedKeywords(articles);
    }

    @Override
    public List<Article> getArticlesByDateRangeAndCategory(ArticleDateRangeAndCategoryDTO articleDateRangeAndCategoryDTO) {
        LocalDateTime start = articleDateRangeAndCategoryDTO.getStartDate().atStartOfDay();
        LocalDateTime end = articleDateRangeAndCategoryDTO.getEndDate().atTime(23, 59, 59);
        List<Article> articles = articleRepository.findVisibleByPublishedAtBetweenAndCategory(start, end, articleDateRangeAndCategoryDTO.getCategoryId());
        return filterBlockedKeywords(articles);
    }

    @Override
    public List<Article> searchArticles(String searchTerm) {
        List<Article> articles = articleRepository.searchVisibleArticles(searchTerm.toLowerCase());
        return filterBlockedKeywords(articles);
    }

    private List<Article> filterBlockedKeywords(List<Article> articles) {
        Set<String> blockedKeywords = getBlockedKeywords();
        if (blockedKeywords.isEmpty()) return articles;
        return articles.stream()
                .filter(a -> blockedKeywords.stream()
                        .noneMatch(k -> a.getTitle().toLowerCase().contains(k) ||
                                a.getDescription().toLowerCase().contains(k)))
                .collect(Collectors.toList());
    }

}
