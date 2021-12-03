package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("/edit/comment/{commentId}")
	public ResponseEntity editComment(@PathVariable Long commentId, @RequestBody Map<String, String> commentForm,
		@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		validateAuthority(commentId, loginUser);
		commentService.edit(commentId, commentForm.get("content"));

		return ResponseEntity.ok().build();
	}

	private void validateAuthority(Long commentId, User loginUser) {
		Comment comment = commentService.findById(commentId).get();

		if (loginUser.getId() != comment.getUser().getId()) {
			throw new NoAuthorityException("권한이 없습니다.");
		}
	}

	@ExceptionHandler(NoAuthorityException.class)
	public ResponseEntity noAuthority() {
		return ResponseEntity.badRequest().build();
	}
}
