package com.learnandcode.news_aggregation_client.dto;

import java.time.LocalDateTime;

public class ExternalServerDTO {
    private String serverName;
    private String status;
    private LocalDateTime lastAccessed;


    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
