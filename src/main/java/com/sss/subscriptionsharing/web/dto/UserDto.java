package com.sss.subscriptionsharing.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

	private String loginId;
	private String password;
	private String name;
	private String nickName;
	private String email;
	private String introduce;
}
