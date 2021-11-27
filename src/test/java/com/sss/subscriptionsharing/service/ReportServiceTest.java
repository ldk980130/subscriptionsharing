package com.sss.subscriptionsharing.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sss.subscriptionsharing.domain.Board;
import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.report.Reason;
import com.sss.subscriptionsharing.domain.report.Report;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.repository.BoardRepository;
import com.sss.subscriptionsharing.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReportServiceTest {

	@Autowired
	private CommentService commentService;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private ReportService reportService;

	@Test
	public void reportPost() throws Exception {
		//given
		Board board = boardRepository.save(Board.create("왓챠"));
		Category category = categoryRepository.save(Category.create("친목", board));

		User user1 = userService.join("ldk", "1234", "이동규",
			"dk", "안녕", "ldk980130@gmail.com");
		User user2 = userService.join("ldk1", "12345", "이동규",
			"dkl", "안녕", "ldk@gmail.com");

		Post post = postService.register(user1.getId(), category.getId(), "제목", "내용");

		//when
		int beforeSize = reportService.findAll().size();
		Report report = reportService.reportPost(user1.getId(), post.getId(), Reason.CURSE);
		reportService.reportPost(user2.getId(), post.getId(), Reason.FALSE);
		int afterSize = reportService.findAll().size();

		//then
		assertThat(beforeSize + 1).isEqualTo(afterSize);
		assertThat(report.getInfos().size()).isEqualTo(2);
	}

	@Test
	public void reportComment() throws Exception {
		//given
		Board board = boardRepository.save(Board.create("왓챠"));
		Category category = categoryRepository.save(Category.create("친목", board));

		User user1 = userService.join("ldk", "1234", "이동규",
			"dk", "안녕", "ldk980130@gmail.com");
		User user2 = userService.join("ldk1", "12345", "이동규",
			"dkl", "안녕", "ldk@gmail.com");

		Post post = postService.register(user1.getId(), category.getId(), "제목", "내용");
		Comment comment = commentService.register(user1.getId(), post.getId(), "댓글1");

		//when
		int beforeSize = reportService.findAll().size();
		Report report = reportService.reportComment(user1.getId(), comment.getId(), Reason.CURSE);
		reportService.reportComment(user2.getId(), comment.getId(), Reason.AD);
		int afterSize = reportService.findAll().size();

		//then
		assertThat(beforeSize + 1).isEqualTo(afterSize);
		assertThat(report.getInfos().size()).isEqualTo(2);
	}

	@Test(expected = IllegalStateException.class)
	public void duplicateReportException() throws Exception {
		//given
		Board board = boardRepository.save(Board.create("왓챠"));
		Category category = categoryRepository.save(Category.create("친목", board));
		User user = userService.join("ldk", "1234", "이동규",
			"dk", "안녕", "ldk980130@gmail.com");
		Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

		//when
		reportService.reportPost(user.getId(), post.getId(), Reason.AD);
		reportService.reportPost(user.getId(), post.getId(), Reason.SLANDERING);

		//then
		fail();
	}
}