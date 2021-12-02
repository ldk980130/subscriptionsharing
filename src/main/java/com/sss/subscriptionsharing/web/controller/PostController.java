package com.sss.subscriptionsharing.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.repository.CategoryRepository;
import com.sss.subscriptionsharing.service.PostService;
import com.sss.subscriptionsharing.web.dto.PostDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final CategoryRepository categoryRepository;

	@GetMapping("/{categoryId}")
	public List<PostDto> categoryPosts(@PathVariable Long categoryId) {

		List<Post> posts = postService.findAllByCategory(categoryId);
		List<PostDto> postDtos = new ArrayList<>();

		for (Post post : posts) {
			postDtos.add(post.toDto());
		}

		return postDtos;
	}
}
