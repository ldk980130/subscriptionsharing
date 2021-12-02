package com.sss.subscriptionsharing.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {

	private Long id;
	private String nickName;
	private String content;

	public CommentDto() {}

	@Builder
	public CommentDto(Long id, String nickName, String content) {
		this.id = id;
		this.nickName = nickName;
		this.content = content;
	}
}
