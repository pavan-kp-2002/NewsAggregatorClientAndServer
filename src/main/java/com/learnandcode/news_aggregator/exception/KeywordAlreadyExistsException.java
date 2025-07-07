package com.learnandcode.news_aggregator.exception;

public class KeywordAlreadyExistsException extends RuntimeException{
    public KeywordAlreadyExistsException(String message){
        super(message);
    }
}
