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

	@Transactional
	public Recommend recommend(Long userId, Long postId) {
		User user = userRepository.findById(userId).get();
		validateAuthority(user);

		Post post = postRepository.findById(postId).get();

		Recommend recommend = Recommend.create(post, user);

		return recommendRepository.save(recommend);
	}

	@Transactional
	public void cancel(Long userId, Long postId) {
		User user = userRepository.findById(userId).get();
		Post post = postRepository.findById(postId).get();

		validateAuthority(user);

		Optional<Recommend> findRecommend = recommendRepository.findByUserAndPost(user, post);

		if (findRecommend.isPresent()) {
			recommendRepository.delete(findRecommend.get());
		}
	}

	private void validateAuthority(User user) {
		if (user.getStatus() == Status.SUSPENSION) {
			throw new NoAuthorityException("권한이 없습니다.");
		}
	}
}
