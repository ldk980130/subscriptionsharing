package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.repository.CommentRepository;
import com.sss.subscriptionsharing.repository.PostRepository;
import com.sss.subscriptionsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment register(Long userId, Long postId, String content) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).get();

        Comment comment = Comment.create(content, post, user);

        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
