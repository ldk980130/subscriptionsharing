package com.sss.subscriptionsharing.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.sss.subscriptionsharing.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MyPage {

	private String name;
	private String nickName;
	private String introduce;

	private List<PostDto> posts = new ArrayList<>();

	public MyPage() {}

	@Builder
	public MyPage(String name, String nickName, String introduce, List<Post> posts) {
		this.name = name;
		this.nickName = nickName;
		this.introduce = introduce;

		for (Post post : posts) {
			this.posts.add(post.toDto());
		}
	}
}
