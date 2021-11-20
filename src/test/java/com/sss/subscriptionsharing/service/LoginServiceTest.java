package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.user.User;
import org.apache.juli.logging.Log;
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
public class LoginServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    @Test
    public void login() throws Exception {
        //given
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //when
        User loginUser = loginService.login("ldk", "1234").get();

        //then
        assertThat(user).isEqualTo(loginUser);
    }

    @Test
    public void findLoginId() throws Exception {
        //given
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //when
        Optional<String> findId = loginService.findLoginId("이동규", "ldk980130@gmail.com");

        //then
        assertThat(findId.get()).isEqualTo("ldk");
    }

    @Test
    public void findPassword() throws Exception {
        //given
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");

        //when
        Optional<String> findPassword = loginService.findPassword("ldk", "ldk980130@gmail.com");

        //then
        assertThat(findPassword.get()).isEqualTo("1234");
    }
}