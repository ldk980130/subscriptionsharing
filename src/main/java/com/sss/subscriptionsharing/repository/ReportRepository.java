package com.sss.subscriptionsharing.repository;

import java.util.Optional;

import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	Optional<Report> findByPost(Post post);
	Optional<Report> findByComment(Comment comment);
}
