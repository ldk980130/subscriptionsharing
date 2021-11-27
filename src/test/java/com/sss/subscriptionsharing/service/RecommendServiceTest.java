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
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.repository.BoardRepository;
import com.sss.subscriptionsharing.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RecommendServiceTest {

	@Autowired private RecommendService recommendService;
	@Autowired private UserService userService;
	@Autowired private PostService postService;
	@Autowired private BoardRepository boardRepository;
	@Autowired private CategoryRepository categoryRepository;

	@Test
	public void recommend() throws Exception {
		//given
		Board board = boardRepository.save(Board.create("왓챠"));
		Category category = categoryRepository.save(Category.create("친목", board));
		User user = userService.join("ldk", "1234", "이동규",
			"dk", "안녕", "ldk980130@gmail.com");
		Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

		//when
		int beforeSize = post.getRecommends().size();
		recommendService.recommend(user.getId(), post.getId());
		int afterSize = post.getRecommends().size();

		//then
		assertThat(beforeSize + 1).isEqualTo(afterSize);
	}

	@Test
	public void cancel() throws Exception {
		//given
		Board board = boardRepository.save(Board.create("왓챠"));
		Category category = categoryRepository.save(Category.create("친목", board));
		User user = userService.join("ldk", "1234", "이동규",
			"dk", "안녕", "ldk980130@gmail.com");
		Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

		//when
		int beforeSize = post.getRecommends().size();
		recommendService.recommend(user.getId(), post.getId());
		recommendService.recommend(user.getId(), post.getId());
		int afterSize = post.getRecommends().size();

		//then
		assertThat(beforeSize).isEqualTo(afterSize);
	}

}