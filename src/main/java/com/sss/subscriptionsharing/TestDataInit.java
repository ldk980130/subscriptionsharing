package com.sss.subscriptionsharing;

import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;

    @PostConstruct
    public void init() {
//        User user1 = userService.join("ldk", "1234", "이동규",
//                "dk", "안녕", "ldk980130@gmail.com");
//
//        User user2 = userService.join("abc", "12344", "김철수",
//                "ch", "안녕", "abc1234@gmail.com");
    }
}
