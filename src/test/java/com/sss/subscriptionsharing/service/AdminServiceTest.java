package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.user.Status;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Test
    public void userSuspension() throws Exception {
        //given
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        User admin = adminService.joinAdmin("admin", "2345", "관리자",
                "ad", "", "1234@gmail.com");

        //when
        adminService.userSuspension(admin.getId(), user.getId());

        //then
        assertThat(user.getStatus()).isEqualTo(Status.SUSPENSION);
        adminService.userSuspensionCancel(admin.getId(), user.getId());
        assertThat(user.getStatus()).isEqualTo(Status.NORMAL);
    }

    @Test(expected = NoAuthorityException.class)
    public void notAdminSuspension() throws Exception {
        //given
        User user = userService.join("ldk", "1234", "이동규",
                "dk", "안녕", "ldk980130@gmail.com");
        User admin = adminService.joinAdmin("admin", "2345", "관리자",
                "ad", "", "1234@gmail.com");

        //when
        adminService.userSuspension(user.getId(), admin.getId());

        //then
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void noExistUserSuspension() throws Exception {
        //given
        User admin = adminService.joinAdmin("admin", "2345", "관리자",
                "ad", "", "1234@gmail.com");

        //when
        adminService.userSuspension(admin.getId(), 100000L);

        //then
        fail();
    }
}