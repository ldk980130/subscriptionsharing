package com.sss.subscriptionsharing.domain.user;

import com.sss.subscriptionsharing.domain.Post;
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

    public static User create (String loginId, String password, String name, String nickName,
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

}
