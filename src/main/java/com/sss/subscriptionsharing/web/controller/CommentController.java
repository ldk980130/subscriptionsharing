package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/create/comment")
	public ResponseEntity createComment(@RequestBody Map<String, String> commentForm,
		@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		log.info("loginUser={}", loginUser.getId());

		commentService.register(loginUser.getId(),
			Long.parseLong(commentForm.get("postId")),
			commentForm.get("content"));

		return ResponseEntity.ok().build();
	}
}
