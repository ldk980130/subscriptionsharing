package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.user.Role;
import com.sss.subscriptionsharing.domain.user.Status;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public User joinAdmin(String loginId, String password, String name, String nickName,
                          String introduce, String email) {
        userService.validateDuplicateId(loginId);
        userService.validateDuplicateNickName(nickName);
        userService.validateDuplicateEmail(email);

        User admin = User.createAdmin(loginId, password, name, nickName, introduce, email);
        userRepository.save(admin);

        return admin;
    }

    @Transactional
    public void userSuspension(Long adminId, Long userId) {
        Optional<User> user = validate(adminId, userId);

        if (user.get().getStatus() == Status.NORMAL) user.get().changeStatus();
    }

    @Transactional
    public void userSuspensionCancel(Long adminId, Long userId) {
        Optional<User> user = validate(adminId, userId);

        if (user.get().getStatus() == Status.SUSPENSION) user.get().changeStatus();
    }

    @Transactional
    public void userEliminate(Long adminId, Long userId) {
        Optional<User> user = validate(adminId, userId);
        userService.withdrawal(user.get().getId());
    }

    private Optional<User> validate(Long adminId, Long userId) {
        validateAdmin(userRepository.findById(adminId));

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new IllegalStateException("사용자가 없습니다");
        return user;
    }

    private void validateAdmin(Optional<User> admin) {
        if (admin.isEmpty() || admin.get().getRole() == Role.MEMBER) {
            throw new NoAuthorityException("관리자 권한이 없습니다.");
        }
    }

}
