package com.learnandcode.news_aggregator.exception;

public class KeywordNotFoundException extends RuntimeException {
    public KeywordNotFoundException(String message) {
        super(message);
    }
}
