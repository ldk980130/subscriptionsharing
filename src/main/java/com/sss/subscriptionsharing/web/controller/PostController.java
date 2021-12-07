package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.service.PostService;
import com.sss.subscriptionsharing.web.dto.PostDto;
import com.sss.subscriptionsharing.web.dto.PostWithCommentDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/home")
	public List<PostDto> home() {

		log.info("모든 게시글 전달");

		List<Post> posts = postService.findAll();
		List<PostDto> postDtos = new ArrayList<>();

		for (Post post : posts) {
			postDtos.add(post.toDto());
		}

		return postDtos;
	}

	// @GetMapping("/home")
	// public String test() {
	// 	log.info("테스트 호출");
	// 	return "hello SSS!";
	// }

	@PostMapping("/create/post")
	public ResponseEntity createPost(@RequestBody Map<String, String> postForm,
		@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		log.info("loginUser={}", loginUser.getId());

		postService.register(loginUser.getId(),
			Long.parseLong(postForm.get("categoryId")),
			postForm.get("title"),
			postForm.get("content"));

		return ResponseEntity.ok().build();
	}

	@PostMapping("/edit/post/{postId}")
	public ResponseEntity editPost(@PathVariable Long postId, @RequestBody Map<String, String> postForm,
		@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		validateAuthority(postId, loginUser);

		postService.edit(postId, postForm.get("title"), postForm.get("content"));

		return ResponseEntity.ok().build();
	}

	private void validateAuthority(Long postId, User loginUser) {
		Post post = postService.findById(postId).get();

		if (loginUser.getId() != post.getUser().getId()) {
			throw new NoAuthorityException("권한이 없습니다.");
		}
	}

	@ExceptionHandler(NoAuthorityException.class)
	public ResponseEntity noAuthority() {
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/delete/post/{postId}")
	public ResponseEntity deletePost(@PathVariable Long postId,
		@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		validateAuthority(postId, loginUser);

		postService.delete(postId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/detail/{postId}")
	public PostWithCommentDto postDetail(@PathVariable Long postId) {

		Post post = postService.findById(postId).get();

		return ResponseEntity.ok(post.toWithCommentDto()).getBody();
	}
}
