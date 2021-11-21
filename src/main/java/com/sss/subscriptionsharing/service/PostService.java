package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.Status;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.repository.CategoryRepository;
import com.sss.subscriptionsharing.repository.PostRepository;
import com.sss.subscriptionsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Post register(Long userId, Long categoryId, String title, String content) {

        User user = userRepository.findById(userId).get();
        validateAuthority(user);
        Category category = categoryRepository.findById(categoryId).get();

        Post post = Post.create(title, content, user, category);

        return postRepository.save(post);
    }

    private void validateAuthority(User user) {
        if (user.getStatus() == Status.SUSPENSION) {
            throw new NoAuthorityException("권한이 없습니다.");
        }
    }

    @Transactional
    public void edit(Long postId, String title, String content) {
        Post post = postRepository.findById(postId).get();
        validateAuthority(post.getUser());

        post.edit(title, content);
    }

    public Optional<Post> findById(Long postId){
        return postRepository.findById(postId);
    }

    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).get();
        validateAuthority(post.getUser());

        post.getCategory().getPosts().remove(post);
        postRepository.delete(post);
    }

}
