package com.sss.subscriptionsharing.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostDto {

	private Long postId;
	private String title;
	private String nickName;
	private String date;
	private String content;

	public PostDto() {}

	@Builder
	public PostDto(Long postId, String title, String nickName, String date, String content) {
		this.postId = postId;
		this.title = title;
		this.nickName = nickName;
		this.date = date;
		this.content = content;
	}
}
