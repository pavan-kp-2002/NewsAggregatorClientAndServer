package com.learnandcode.news_aggregation_client.dto;

public class ExternalServerDetailDTO {
    public Long serverId;
    public String serverName;
    public String apiKey;
    public String endPoint;

    public Long getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }


    public String getApiKey() {
        return apiKey;
    }


    public String getEndPoint() {
        return endPoint;
    }

}
