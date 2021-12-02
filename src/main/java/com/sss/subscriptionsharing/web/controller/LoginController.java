package com.sss.subscriptionsharing.web.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

	static final String LOGIN_USER = "loginUser";
	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Map<String, String> loginForm, HttpServletRequest request) {

		Optional<User> loginUser = loginService.login(loginForm.get("loginId"), loginForm.get("password"));

		if (loginUser.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		HttpSession session = request.getSession();
		session.setAttribute(LOGIN_USER, loginUser.get().getLoginId());

		log.info("로그인 성공={}", session.getAttribute(LOGIN_USER));

		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			session.invalidate();
		}

		return ResponseEntity.ok().build();
	}
}
