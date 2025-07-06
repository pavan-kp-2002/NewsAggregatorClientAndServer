package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.service.ArticleModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/moderation")
@PreAuthorize("hasRole('ADMIN')")
public class AdminModerationController {
    @Autowired
    private ArticleModerationService moderationService;

    @PostMapping("/report/{articleId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> reportArticle(@PathVariable Long articleId) {
        moderationService.reportArticle(articleId);
        return ResponseEntity.ok("Article reported successfully.");
    }

    @PostMapping("/hide/article/{articleId}")
    public ResponseEntity<String> hideArticle(@PathVariable Long articleId) {
        moderationService.hideArticle(articleId);
        return ResponseEntity.ok("Article hidden successfully.");
    }

    @PostMapping("/unhide/article/{articleId}")
    public ResponseEntity<String> unhideArticle(@PathVariable Long articleId) {
        moderationService.unhideArticle(articleId);

        return ResponseEntity.ok("Article unhidden successfully.");
    }

    @PostMapping("/hide/category/{categoryId}")
    public ResponseEntity<String> hideCategory(@PathVariable Long categoryId) {
        moderationService.hideCategory(categoryId);
        return ResponseEntity.ok("Category hidden successfully.");
    }

    @PostMapping("/unhide/category/{categoryId}")
    public ResponseEntity<String> unhideCategory(@PathVariable Long categoryId) {
        moderationService.unhideCategory(categoryId);
        return ResponseEntity.ok("Category unhidden successfully.");
    }

    @PostMapping("/block-keyword")
    public ResponseEntity<String> addBlockedKeyword(@RequestParam String keyword) {
        moderationService.addBlockedKeyword(keyword);
        return ResponseEntity.ok("Keyword blocked successfully.");
    }

    @DeleteMapping("/block-keyword")
    public ResponseEntity<String> removeBlockedKeyword(@RequestParam String keyword) {
        moderationService.removeBlockedKeyword(keyword);
        return ResponseEntity.ok("Keyword unblocked successfully.");
    }

}
