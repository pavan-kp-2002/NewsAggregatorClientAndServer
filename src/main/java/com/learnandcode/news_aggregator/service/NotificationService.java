package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.NotificationDTO;
import com.learnandcode.news_aggregator.model.Notification;
import com.learnandcode.news_aggregator.model.User;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotificationsForUser();
}
