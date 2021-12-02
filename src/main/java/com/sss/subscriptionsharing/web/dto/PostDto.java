package com.sss.subscriptionsharing.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.sss.subscriptionsharing.domain.Comment;

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

	private List<CommentDto> comments = new ArrayList<>();

	public PostDto() {}

	@Builder
	public PostDto(Long postId, String title, String nickName, String date, String content, List<Comment> comments) {
		this.postId = postId;
		this.title = title;
		this.nickName = nickName;
		this.date = date;
		this.content = content;

		for (Comment comment : comments) {
			this.comments.add(comment.toDto());
		}

	}
}
