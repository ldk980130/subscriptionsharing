package com.sss.subscriptionsharing.domain.user;

import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.web.dto.MyPage;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    private String introduce;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public static User create(String loginId, String password, String name, String nickName,
                               String introduce, String email) {
        User user = new User();
        user.loginId = loginId;
        user.password = password;
        user.name = name;
        user.nickName = nickName;
        user.introduce = introduce;
        user.email = email;

        user.role = Role.MEMBER;
        user.status = Status.NORMAL;

        return user;
    }

    public static User createAdmin(String loginId, String password, String name, String nickName,
                              String introduce, String email) {
        User user = new User();
        user.loginId = loginId;
        user.password = password;
        user.name = name;
        user.nickName = nickName;
        user.introduce = introduce;
        user.email = email;

        user.role = Role.ADMIN;
        user.status = Status.NORMAL;

        return user;
    }

    public void edit(String loginId, String password, String name, String nickName,
                     String introduce, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.introduce = introduce;
        this.email = email;
    }

    public Status changeStatus() {
        if (this.status == Status.NORMAL) {
            this.status = Status.SUSPENSION;
            return Status.SUSPENSION;
        }

        this.status = Status.NORMAL;
        return Status.NORMAL;
    }

    public MyPage toMyPage() {
        return MyPage.builder()
            .name(this.name)
            .nickName(this.nickName)
            .introduce(this.introduce)
            .posts(this.posts)
            .build();
    }

}
