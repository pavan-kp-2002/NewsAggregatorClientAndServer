package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.ReportNotificationResponseDTO;

import java.util.List;

public interface ReportNotificationService {
    List<ReportNotificationResponseDTO> getAllReportNotifications();
    List<ReportNotificationResponseDTO> getUnreadReportNotifications();

}
