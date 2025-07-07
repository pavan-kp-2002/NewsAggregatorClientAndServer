package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.ReportNotification;
import com.learnandcode.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportNotificationRepository extends JpaRepository<ReportNotification, Long> {
    List<ReportNotification> findAllByAdminAndIsReadFalse(User admin);
    List<ReportNotification> findAllByAdmin(User admin);
}
