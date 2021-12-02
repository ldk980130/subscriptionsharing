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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Transactional
    public Post register(Long userId, Long categoryId, String title, String content) {

        User user = userRepository.findById(userId).get();
        userService.validateAuthority(user);
        Category category = categoryRepository.findById(categoryId).get();

        Post post = Post.create(title, content, user, category);

        return postRepository.save(post);
    }

    @Transactional
    public void edit(Long postId, String title, String content) {
        Post post = postRepository.findById(postId).get();
        userService.validateAuthority(post.getUser());

        post.edit(title, content);
    }

    public Optional<Post> findById(Long postId){
        return postRepository.findById(postId);
    }

    public List<Post> findPageByCategory(Long categoryId, int start, int max) {
        Category category = categoryRepository.findById(categoryId).get();
        PageRequest pageRequest = PageRequest.of(start, max, Sort.Direction.ASC, "date");
        return postRepository.findAllByCategory(category, pageRequest);
    }

    public List<Post> findAllByCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return postRepository.findAllByCategory(category.get());
    }

    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).get();
        userService.validateAuthority(post.getUser());

        post.getCategory().getPosts().remove(post);
        postRepository.delete(post);
    }

}
