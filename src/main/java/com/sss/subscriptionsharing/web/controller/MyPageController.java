package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.service.UserService;
import com.sss.subscriptionsharing.web.dto.MyPage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

	private final UserService userService;

	@GetMapping("/mypage")
	public MyPage myPage(@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		log.info("마이페이지 컨트롤러 호출");
		User user = userService.findByLoginId(loginUser.getLoginId()).get();
		log.info("로그인 유저 닉네임={}", user.getNickName());

		return user.toMyPage();
	}
}
