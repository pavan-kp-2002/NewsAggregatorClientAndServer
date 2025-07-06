package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByUrl(String url);
//    List<Article> findByPublishedAtBetween(LocalDateTime start, LocalDateTime end);
//    List<Article> findByPublishedAtBetweenAndCategoryId_IdOrderByPublishedAtDesc(LocalDateTime start, LocalDateTime end, Long categoryId);
//    List<Article> findByTitleContainingIgnoreCaseOrderByPublishedAtDesc(String searchTerm);
    @Query("SELECT a FROM Article a WHERE a.hidden = false AND a.categoryId.hidden = false AND a.publishedAt BETWEEN :start AND :end")
    List<Article> findVisibleByPublishedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT a FROM Article a WHERE a.hidden = false AND a.categoryId.hidden = false AND a.categoryId.id = :categoryId AND a.publishedAt BETWEEN :start AND :end ORDER BY a.publishedAt DESC")
    List<Article> findVisibleByPublishedAtBetweenAndCategory(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("categoryId") Long categoryId);

    @Query("SELECT a FROM Article a WHERE a.hidden = false AND a.categoryId.hidden = false AND LOWER(a.title) LIKE %:searchTerm% ORDER BY a.publishedAt DESC")
    List<Article> searchVisibleArticles(@Param("searchTerm") String searchTerm);
}

