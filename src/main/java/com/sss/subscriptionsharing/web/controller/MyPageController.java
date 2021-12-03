package com.sss.subscriptionsharing.web.controller;

import static com.sss.subscriptionsharing.web.SessionConst.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.service.UserService;
import com.sss.subscriptionsharing.web.dto.MyPage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyPageController {

	private final UserService userService;

	@GetMapping("/mypage")
	public MyPage myPage(@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {

		User user = userService.findByLoginId(loginUser.getLoginId()).get();

		return user.toMyPage();
	}
}
