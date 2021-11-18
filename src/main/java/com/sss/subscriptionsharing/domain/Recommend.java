package com.sss.subscriptionsharing.domain;

import com.sss.subscriptionsharing.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Recommend() {
    }

    public static Recommend create(Post post, User user) {
        Recommend recommend = new Recommend();

        recommend.post = post;
        post.getRecommends().add(recommend);

        recommend.user = user;
        return recommend;
    }
}
