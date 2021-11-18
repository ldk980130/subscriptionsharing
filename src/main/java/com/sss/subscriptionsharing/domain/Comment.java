package com.sss.subscriptionsharing.domain;

import com.sss.subscriptionsharing.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Comment() {
    }

    public static Comment create(String content, Post post, User user) {
        Comment comment = new Comment();
        comment.content = content;
        comment.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        comment.user = user;

        post.getComments().add(comment);
        comment.post = post;

        return comment;
    }

    public void edit(String content) {
        this.content = content;
    }
}
