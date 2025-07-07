package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.ReportNotificationResponseDTO;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ReportNotification;
import com.learnandcode.news_aggregator.service.ReportNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/report-notification")
public class ReportNotificationController {
    @Autowired
    private ReportNotificationService reportNotificationService;

    @GetMapping("/all")
    public ResponseEntity<List<ReportNotificationResponseDTO>> getAllNotifications() {
        List<ReportNotificationResponseDTO> notifications = reportNotificationService.getAllReportNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/new")
    public ResponseEntity<List<ReportNotificationResponseDTO>> getNewNotifications() {
        List<ReportNotificationResponseDTO> notifications = reportNotificationService.getUnreadReportNotifications();
        return ResponseEntity.ok(notifications);
    }
}
