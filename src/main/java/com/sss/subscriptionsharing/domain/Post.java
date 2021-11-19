package com.sss.subscriptionsharing.domain;

import com.sss.subscriptionsharing.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Recommend> recommends = new ArrayList<>();

    protected Post() {
    }

    public static Post create(String title, String content, User user, Category category) {
        Post post = new Post();
        post.title = title;
        post.content = content;

        post.user = user;
        user.getPosts().add(post);

        post.category = category;
        category.getPosts().add(post);

        post.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return post;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
