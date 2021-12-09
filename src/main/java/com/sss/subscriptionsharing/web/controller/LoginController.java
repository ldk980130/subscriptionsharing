package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

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

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Map<String, String> loginForm, HttpServletRequest request) {

		log.info("로그인 컨트롤러 호출");

		log.info("데이터={}, {}", loginForm.get("loginId"), loginForm.get("password"));

		Optional<User> loginUser = loginService.login(loginForm.get("loginId"), loginForm.get("password"));

		if (loginUser.isEmpty()) {
			log.info("로그인 실패");
			return ResponseEntity.badRequest().build();
		}

		HttpSession session = request.getSession();
		session.setAttribute(LOGIN_USER, loginUser.get());

		log.info("로그인 성공={}", session.getAttribute(LOGIN_USER));

		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		log.info("로그아웃 컨트롤러 호출");
		HttpSession session = request.getSession();

		if (session != null) {
			session.invalidate();
		}

		return ResponseEntity.ok().build();
	}
}
