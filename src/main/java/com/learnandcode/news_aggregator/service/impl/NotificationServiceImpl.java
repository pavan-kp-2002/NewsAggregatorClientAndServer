package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.NotificationDTO;
import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.Notification;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.repositories.NotificationRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    private static final Logger logger = LoggerFactory.getLogger(NewsFetchServiceImpl.class);
    @Override
    public List<NotificationDTO> getNotificationsForUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User with the given username does not exist"));
        List<Notification> notificationList = notificationRepository.findByUserAndEmailSentFalse(user);
        if (notificationList.isEmpty()) {
            logger.info("No notifications found for user: {}", userName);
            throw new UserNotFoundException("No notifications found for the user " + userName);
        } else {
            List<NotificationDTO> notificationDTOList = new ArrayList<>();
            for (Notification notification : notificationList) {
                notificationDTOList.add(new NotificationDTO(
                        notification.getArticle().getArticleId(),
                        notification.getArticle().getTitle(),
                        notification.getArticle().getUrl()));
                notification.setEmailSent(true);
                notification.setNotificationRead(true);
            }
            notificationRepository.saveAll(notificationList);
            return notificationDTOList;
        }
    }
}
