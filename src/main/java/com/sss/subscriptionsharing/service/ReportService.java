package com.sss.subscriptionsharing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.report.Reason;
import com.sss.subscriptionsharing.domain.report.Report;
import com.sss.subscriptionsharing.domain.report.ReportInfo;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.repository.CommentRepository;
import com.sss.subscriptionsharing.repository.PostRepository;
import com.sss.subscriptionsharing.repository.ReportRepository;
import com.sss.subscriptionsharing.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public Report reportPost(Long userId, Long postId, Reason reason) {

		User user = userRepository.findById(userId).get();
		Post post = postRepository.findById(postId).get();

		Optional<Report> findReport = reportRepository.findByPost(post);
		Report report;

		if (findReport.isPresent()) {
			report = addReportInfo(reason, user, findReport);
			return report;
		}

		report = Report.createOfPost(post, user, reason);
		return reportRepository.save(report);
	}

	private Report addReportInfo(Reason reason, User user, Optional<Report> findReport) {
		Report report = findReport.get();
		validateDuplicateReport(user, report);
		report.addInfo(user, reason);
		return report;
	}

	@Transactional
	public Report reportComment(Long userId, Long commentId, Reason reason) {

		User user = userRepository.findById(userId).get();
		Comment comment = commentRepository.findById(commentId).get();

		Optional<Report> findReport = reportRepository.findByComment(comment);
		Report report;

		if (findReport.isPresent()) {
			report = addReportInfo(reason, user, findReport);
			return report;
		}

		report = Report.createOfComment(comment, user, reason);
		return reportRepository.save(report);
	}

	private void validateDuplicateReport(User user, Report report) {
		Optional<ReportInfo> findInfo = report.getInfos()
			.stream()
			.filter(r -> r.getUser() == user)
			.findFirst();

		if (findInfo.isPresent()) {
			throw new IllegalStateException("이미 신고한 컨텐츠 입니다.");
		}
	}

	public List<Report> findAllOfPost() {
		return reportRepository.findAllByPostIsNotNull();
	}

	public List<Report> findAllOfComment() {
		return reportRepository.findAllByCommentIsNotNull();
	}

	public Optional<Report> findByPost(Long postId) {
		Post post = postRepository.findById(postId).get();
		return reportRepository.findByPost(post);
	}

	public Optional<Report> findByComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId).get();
		return reportRepository.findByComment(comment);
	}

	public List<Report> findAll() {
		return reportRepository.findAll();
	}
}
