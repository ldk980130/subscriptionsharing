package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.Board;
import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.domain.Comment;
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

import java.util.List;
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
    @Autowired
    private CommentService commentService;

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
        postService.register(user.getId(), category.getId(), "제목", "내용");

        //then
        fail();
    }

    @Test
    public void postEdit() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");

        //when
        postService.edit(post.getId(), "바뀐 제목", "바뀐 내용");
        String title = postService.findById(post.getId()).get().getTitle();
        String content = postService.findById(post.getId()).get().getContent();

        //then
        assertThat(title).isEqualTo("바뀐 제목");
        assertThat(content).isEqualTo("바뀐 내용");
    }

    @Test
    public void paging() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post1 = postService.register(user.getId(), category.getId(), "제목", "내용");
        Post post2 = postService.register(user.getId(), category.getId(), "제목", "내용");
        Post post3 = postService.register(user.getId(), category.getId(), "제목", "내용");
        Post post4 = postService.register(user.getId(), category.getId(), "제목", "내용");

        //when
        int size = category.getPosts().size();
        List<Post> pages = postService.findPageByCategory(category.getId(), 0, size - 1);

        //then
        assertThat(pages.size()).isEqualTo(size - 1);
    }

    @Test
    public void postDelete() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");
        Long postId = post.getId();

        //when
        int categoryPosts = category.getPosts().size();
        postService.delete(postId);
        Optional<Post> findPost = postService.findById(postId);

        //then
        assertThat(findPost).isEmpty();
        assertThat(category.getPosts().size()).isEqualTo(categoryPosts - 1);
    }

    @Test
    public void postDeleteWhenHaveComment() throws Exception {
        //given
        Board board = boardRepository.save(Board.create("왓챠"));
        Category category = categoryRepository.save(Category.create("친목", board));
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        Post post = postService.register(user.getId(), category.getId(), "제목", "내용");
        Long postId = post.getId();
        Comment comment = commentService.register(user.getId(), postId, "댓글");
        Long commentId = comment.getId();

        //when
        int categoryPosts = category.getPosts().size();
        postService.delete(postId);
        Optional<Post> findPost = postService.findById(postId);

        //then
        assertThat(findPost).isEmpty();
        assertThat(category.getPosts().size()).isEqualTo(categoryPosts - 1);

        Optional<Comment> findComment = commentService.findById(commentId);
        assertThat(findComment).isEmpty();
    }
}