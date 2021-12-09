package com.sss.subscriptionsharing.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sss.subscriptionsharing.service.UserService;
import com.sss.subscriptionsharing.web.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity signUp(@RequestBody UserDto userDto) {

		userService.join(userDto.getLoginId(),
			userDto.getPassword(),
			userDto.getName(),
			userDto.getNickName(),
			userDto.getIntroduce(),
			userDto.getEmail());

		log.info("회원가입 완료");

		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity signUpException() {
		return ResponseEntity.badRequest().build();
	}
}
