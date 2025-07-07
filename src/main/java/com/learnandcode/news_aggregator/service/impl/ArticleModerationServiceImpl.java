package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.exception.ArticleNotFoundException;
import com.learnandcode.news_aggregator.model.*;
import com.learnandcode.news_aggregator.repositories.*;
import com.learnandcode.news_aggregator.service.ArticleModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleModerationServiceImpl implements ArticleModerationService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BlockedKeywordRepository blockedKeywordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportNotificationRepository reportNotificationRepository;

    @Override
    public void reportArticle(Long articleId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if (userOpted.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOpted.get();
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        boolean alreadyReported = reportRepository.existsByUserAndArticle(user, article);
        if (alreadyReported) {
            throw new RuntimeException("You have already reported this article");
        }
        Report report = new Report();
        report.setUser(user);
        report.setArticle(article);
        reportRepository.save(report);
        List<User> adminUsers = userRepository.findByUserRole(UserRole.ADMIN);
        List<ReportNotification> reportNotificationList = new ArrayList<>();
        for(User admin : adminUsers){
            ReportNotification reportNotification = new ReportNotification();
            reportNotification.setReporter(user);
            reportNotification.setAdmin(admin);
            reportNotification.setArticle(article);
            reportNotification.setCreatedAt(LocalDateTime.now());
            reportNotification.setMessage("An article with ID " + articleId + "and Title: " + article.getTitle() + " has been reported by " + user.getUsername());
            reportNotification.setRead(false);
            reportNotificationList.add(reportNotification);
        }

        reportNotificationRepository.saveAll(reportNotificationList);

        long reportCount = reportRepository.countByArticle(article);
        int REPORT_THRESHOLD = 3;
        if (reportCount >= REPORT_THRESHOLD) {
            article.setHidden(true);
            articleRepository.save(article);
        }
    }

    @Override
    public void hideArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        if (!article.isHidden()) {
            article.setHidden(true);
            articleRepository.save(article);
        }
    }

    @Override
    public void unhideArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        if (article.isHidden()) {
            article.setHidden(false);
            articleRepository.save(article);
        }
    }

    @Override
    public void hideCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (!category.isHidden()) {
            category.setHidden(true);
            categoryRepository.save(category);
        }
    }

    @Override
    public void unhideCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.isHidden()) {
            category.setHidden(false);
            categoryRepository.save(category);
        }
    }

    @Override
    public void addBlockedKeyword(String keyword) {
        if (!blockedKeywordRepository.existsByKeywordIgnoreCase(keyword)) {
            BlockedKeyword blockedKeyword = new BlockedKeyword();
            blockedKeyword.setKeyword(keyword);
            blockedKeywordRepository.save(blockedKeyword);
        }
    }

    @Override
    public void removeBlockedKeyword(String keyword) {
        blockedKeywordRepository.deleteByKeywordIgnoreCase(keyword);
    }
}
