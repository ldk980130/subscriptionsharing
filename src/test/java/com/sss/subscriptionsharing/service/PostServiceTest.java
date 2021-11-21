package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.Board;
import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.repository.BoardRepository;
import com.sss.subscriptionsharing.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserService userService;

    @Test
    public void postRegister() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //when
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

        //then
        Post findPost = postService.findById(post.getId()).get();
        assertThat(findPost).isEqualTo(post);
    }

    @Test(expected = NoAuthorityException.class)
    public void validateUserAuthority() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //when
        user.changeStatus();
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

        //then
        fail();
    }
}