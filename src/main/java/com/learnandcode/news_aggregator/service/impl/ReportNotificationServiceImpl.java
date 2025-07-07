package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.ReportNotificationResponseDTO;
import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.ReportNotification;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.repositories.ReportNotificationRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.ReportNotificationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportNotificationServiceImpl implements ReportNotificationService {
    @Autowired
    private ReportNotificationRepository reportNotificationRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReportNotificationServiceImpl.class);

    @Override
    public List<ReportNotificationResponseDTO> getAllReportNotifications() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if(userOpted.isEmpty()){
            logger.error("User with username {} not found while getting report notifications", userName);
            throw new UserNotFoundException("User with the given username does not exist");
        }
        List<ReportNotification> notifications =  reportNotificationRepository.findAllByAdmin(userOpted.get());
        for (ReportNotification notification : notifications) {
            notification.setRead(true);
        }
        reportNotificationRepository.saveAll(notifications);

        return notifications.stream()
                .map(notification -> new ReportNotificationResponseDTO(
                        notification.getId(),
                        notification.isRead(),
                        notification.getCreatedAt(),
                        notification.getMessage(),
                        notification.getArticle().getTitle(),
                        notification.getReporter().getUsername(),
                        notification.getAdmin().getUsername(),
                        notification.getArticle().getArticleId()
                        ))
                .toList();
    }

    @Override
    @Transactional
    public List<ReportNotificationResponseDTO> getUnreadReportNotifications() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if(userOpted.isEmpty()){
            logger.error("User with username {} not found while getting unread report notifications", userName);
            throw new UserNotFoundException("User with the given username does not exist");
        }
        List<ReportNotification> notifications =  reportNotificationRepository.findAllByAdminAndIsReadFalse(userOpted.get());
        for (ReportNotification notification : notifications) {
            notification.setRead(true);
        }
        reportNotificationRepository.saveAll(notifications);

        return notifications.stream()
                .map(notification -> new ReportNotificationResponseDTO(
                        notification.getId(),
                        notification.isRead(),
                        notification.getCreatedAt(),
                        notification.getMessage(),
                        notification.getArticle().getTitle(),
                        notification.getReporter().getUsername(),
                        notification.getAdmin().getUsername(),
                        notification.getArticle().getArticleId()
                ))
                .toList();
    }
}
