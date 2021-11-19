package com.sss.subscriptionsharing.repository;

import com.sss.subscriptionsharing.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}
