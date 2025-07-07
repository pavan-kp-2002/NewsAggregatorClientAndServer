package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.factory.NewsApiHandlerFactory;
import com.learnandcode.news_aggregator.model.*;
import com.learnandcode.news_aggregator.repositories.*;
import com.learnandcode.news_aggregator.service.EmailService;
import com.learnandcode.news_aggregator.service.ExternalNewsApiHandler;
import com.learnandcode.news_aggregator.service.NewsFetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NewsFetchServiceImpl implements NewsFetchService {
    @Autowired
    private ExternalServerRepository externalServerRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private NewsApiHandlerFactory handlerFactory;
    @Autowired
    private UserCategoryConfigurationRepository userNotificationConfigurationRepo;
    @Autowired
    private UserKeywordConfigurationRepository userKeywordConfigurationRepo;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(NewsFetchServiceImpl.class);
    @org.springframework.scheduling.annotation.Async
    public void sendPendingNotificationsForUser(User user) {
        List<Notification> pending = notificationRepository.findByUserAndEmailSentFalse(user);
        if (pending.isEmpty()) return;

        StringBuilder body = new StringBuilder("Hello " + user.getUsername() + ",\n\nYou have new articles:\n");
        for (Notification n : pending) {
            Article article = n.getArticle();
            body.append("- ID: ").append(article.getArticleId())
                .append(", Title: ").append(article.getTitle())
                .append(", Link: ").append(article.getUrl())
                .append("\n");
            n.setEmailSent(true);
        }
        emailService.sendEmail(user.getEmail(), "Your News Notifications", body.toString());
        notificationRepository.saveAll(pending);
    }

    private final long fetchInterval = 4 * 60 * 60 * 1000;
    private final long testInterval = fetchInterval;
    @Override
    @Scheduled(fixedRate = testInterval)
    public void fetchArticlesFromAllExternalApis() {
       List<ExternalServer> externalServers = externalServerRepository.findAll();

       for (ExternalServer server : externalServers) {
            try {
                ExternalNewsApiHandler handler = handlerFactory.getHandler(server.getServerName());
                List<Article> articles = handler.fetchArticles(server);
                articleRepository.saveAll(articles);

                for (Article article : articles) {
                    Set<Long> notifieduserIds = new HashSet<>();
                    List<Notification> notificationsToSave = new ArrayList<>();

                    if (article.getCategoryId() != null) {
                        List<UserCategoryConfiguration> categoryConfigs =
                                userNotificationConfigurationRepo.findByCategoryAndNotificationConfigurationStatus(
                                        article.getCategoryId(), NotificationConfigurationStatus.ENABLED);

                        for (UserCategoryConfiguration config : categoryConfigs) {
                            long userId = config.getUser().getUserId();
                            if(!notifieduserIds.contains(userId)){
                                Notification notification = new Notification();
                                notification.setArticle(article);
                                notification.setUser(config.getUser());
                                notification.setNotificationRead(false);
                                notification.setEmailSent(false);
                                notificationsToSave.add(notification);
                                notifieduserIds.add(userId);
                            }

                        }
                    }

                    // 2. Keyword-based notifications
                    List<UserKeywordConfiguration> keywordConfigs = userKeywordConfigurationRepo.findBykeywordConfigurationStatus(NotificationConfigurationStatus.ENABLED);
                    for (UserKeywordConfiguration config : keywordConfigs) {
                        long userId = config.getUser().getUserId();
                        if(notifieduserIds.contains(userId)) continue;

                        String keyword = config.getKeyword().toLowerCase();
                        if ((article.getTitle() != null && article.getTitle().toLowerCase().contains(keyword)) ||
                                (article.getDescription() != null && article.getDescription().toLowerCase().contains(keyword))) {

                            Notification notification = new Notification();
                            notification.setArticle(article);
                            notification.setUser(config.getUser());
                            notification.setNotificationRead(false);
                            notification.setEmailSent(false);
                            notificationsToSave.add(notification);
                            notifieduserIds.add(userId);
                        }
                    }

                    notificationRepository.saveAll(notificationsToSave);

                    Set<User> affectedUsers = new HashSet<>();
                    for (Notification n : notificationsToSave) {
                        affectedUsers.add(n.getUser());
                    }
                    for (User user : affectedUsers) {
                        sendPendingNotificationsForUser(user);
                    }
                }
                server.setStatus(ServerStatus.ACTIVE);
            }catch (Exception e){
                logger.error("Error fetching articles from server {}: {}", server.getServerName(), e.getMessage());
                server.setStatus(ServerStatus.INACTIVE);
                System.out.println(e.getMessage());
            }
            server.setLastAccessed(LocalDateTime.now());
            externalServerRepository.save(server);
        }
    }
}
