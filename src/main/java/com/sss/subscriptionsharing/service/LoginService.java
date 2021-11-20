package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;

    public Optional<User> login(String loginId, String password) {
        return userService.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password));
    }

    public Optional<String> findLoginId(String name, String email) {
        List<User> userList = userService.findByName(name);
        Optional<User> findUser = userList.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();

        if (findUser.isPresent()) return Optional.of(findUser.get().getLoginId());

        return Optional.empty();
    }

    public Optional<String> findPassword(String loginId, String email) {
        Optional<User> findUser = userService.findByLoginId(loginId);

        if (findUser.isPresent() && findUser.get().getEmail().equals(email)) {
            return Optional.of(findUser.get().getPassword());
        }

        return Optional.empty();
    }
}
