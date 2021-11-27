package com.sss.subscriptionsharing.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.Recommend;
import com.sss.subscriptionsharing.domain.user.Status;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.repository.PostRepository;
import com.sss.subscriptionsharing.repository.RecommendRepository;
import com.sss.subscriptionsharing.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {

	private final RecommendRepository recommendRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final UserService userService;

	@Transactional
	public Optional<Recommend> recommend(Long userId, Long postId) {
		User user = userRepository.findById(userId).get();
		userService.validateAuthority(user);

		Post post = postRepository.findById(postId).get();

		Optional<Recommend> findRecommend = recommendRepository.findByUserAndPost(user, post);

		if (findRecommend.isPresent()) {
			cancel(findRecommend.get());
			return Optional.empty();
		}

		Recommend recommend = Recommend.create(post, user);

		return Optional.of(recommendRepository.save(recommend));
	}

	@Transactional
	public void cancel(Recommend recommend) {

		recommend.getPost().getRecommends().remove(recommend);
		recommendRepository.delete(recommend);
	}
}
