package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.Board;
import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.repository.BoardRepository;
import com.sss.subscriptionsharing.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Test
    public void commentRegister() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

        //when
        Comment comment = commentService.register(user.getId(), post.getId(), "댓글1");

        //then
        Comment findComment = commentService.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo("댓글1");
    }

    @Test
    public void commentEdit() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");
        Comment comment = commentService.register(user.getId(), post.getId(), "댓글1");

        //when
        commentService.edit(comment.getId(), "수정 댓글");
        Comment findComment = commentService.findById(comment.getId()).get();
        String content = findComment.getContent();

        //then
        assertThat(content).isEqualTo("수정 댓글");

    }

    @Test
    public void commentDelete() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");
        Comment comment = commentService.register(user.getId(), post.getId(), "댓글1");
        Long commentId = comment.getId();

        //when
        int postComments = post.getComments().size();
        commentService.delete(commentId);
        Optional<Comment> findComment = commentService.findById(commentId);

        //then
        assertThat(findComment).isEmpty();
        assertThat(post.getComments().size()).isEqualTo(postComments - 1);
    }
}