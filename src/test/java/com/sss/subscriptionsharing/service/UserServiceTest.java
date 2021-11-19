package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DisplayName("UserService 테스트")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void 회원가입() throws Exception {
        //given
        //when
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        //then
        User findUser = userService.findByLoginId("ldk").get();
        assertThat(user).isEqualTo(findUser);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복아이디검사() throws Exception {
        //given

        //when
        User user1 = userService.join("ldk", "1234", "이동규",
                "dka", "안녕", "980130@gmail.com");
        User user2 = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //then
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void 중복닉네임검사() throws Exception {
        //given

        //when
        User user1 = userService.join("ldk2", "1234", "이동규",
                "dk", "안녕", "980130@gmail.com");
        User user2 = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //then
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void 중복이메일검사() throws Exception {
        //given

        //when
        User user1 = userService.join("ldk1", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        User user2 = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //then
        fail();
    }
}