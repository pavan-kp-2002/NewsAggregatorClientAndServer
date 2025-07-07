package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.Report;
import com.learnandcode.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByArticle(Article article);
    List<Report> findByArticle(Article article);
    boolean existsByUserAndArticle(User user, Article article);
}
