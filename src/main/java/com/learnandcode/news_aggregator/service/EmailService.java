package com.learnandcode.news_aggregator.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
