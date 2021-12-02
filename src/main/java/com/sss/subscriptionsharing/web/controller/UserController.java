package com.sss.subscriptionsharing.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sss.subscriptionsharing.service.UserService;
import com.sss.subscriptionsharing.web.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RestController
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

		return ResponseEntity.ok().build();
	}
}
