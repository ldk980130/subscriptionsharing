package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.service.PostService;
import com.sss.subscriptionsharing.service.UserService;
import com.sss.subscriptionsharing.web.dto.PostDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final UserService userService;

	@GetMapping("/{categoryId}")
	public List<PostDto> categoryPosts(@PathVariable Long categoryId) {

		List<Post> posts = postService.findAllByCategory(categoryId);
		List<PostDto> postDtos = new ArrayList<>();

		for (Post post : posts) {
			postDtos.add(post.toDto());
		}

		return postDtos;
	}

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
	public ResponseEntity editPost(@PathVariable Long postId, @RequestBody Map<String, String> postForm) {

		postService.edit(postId, postForm.get("title"), postForm.get("content"));

		return ResponseEntity.ok().build();
	}

	@GetMapping("/detail/{postId}")
	public PostDto postDetail(@PathVariable Long postId) {

		Post post = postService.findById(postId).get();

		return ResponseEntity.ok(post.toDto()).getBody();
	}
}
