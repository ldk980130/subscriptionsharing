package com.sss.subscriptionsharing.repository;

import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCategory(Category category);
    List<Post> findAllByCategory(Category category, Pageable pageable);
}
