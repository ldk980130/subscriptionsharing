package com.sss.subscriptionsharing.repository;

import java.util.Optional;

import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.Recommend;
import com.sss.subscriptionsharing.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {

	Optional<Recommend> findByUserAndPost(User user, Post post);
}
