package com.learnandcode.news_aggregation_client.dto;

import java.time.LocalDateTime;

public class ReportNotificationDTO {
    private Long id;
    private String message;
    private String articleTitle;
    private Long articleId;
    private String reportedName;
    private String adminName;
    private LocalDateTime createdAt;
    private boolean read;

    public ReportNotificationDTO(Long id, boolean read, LocalDateTime createdAt, String message, String articleTitle, String reportedName, String adminName, Long articleId) {
        this.id = id;
        this.read = read;
        this.createdAt = createdAt;
        this.message = message;
        this.articleTitle = articleTitle;
        this.reportedName = reportedName;
        this.adminName = adminName;
        this.articleId = articleId;
    }


    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportedName() {
        return reportedName;
    }

    public void setReportedName(String reportedName) {
        this.reportedName = reportedName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
