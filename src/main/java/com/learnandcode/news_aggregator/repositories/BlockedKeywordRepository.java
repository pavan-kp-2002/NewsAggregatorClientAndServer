package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.BlockedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedKeywordRepository extends JpaRepository<BlockedKeyword, Long> {
    boolean existsByKeywordIgnoreCase(String keyword);
    void deleteByKeywordIgnoreCase(String keyword);
}
