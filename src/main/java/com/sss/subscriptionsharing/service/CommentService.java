package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.Status;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
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
    private final UserService userService;

    @Transactional
    public Comment register(Long userId, Long postId, String content) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).get();

        userService.validateAuthority(user);

        Comment comment = Comment.create(content, post, user);

        return commentRepository.save(comment);
    }

    @Transactional
    public void edit(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).get();
        userService.validateAuthority(comment.getUser());

        comment.edit(content);
    }

    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        userService.validateAuthority(comment.getUser());

        comment.getPost().getComments().remove(comment);
        commentRepository.delete(comment);
    }
}
