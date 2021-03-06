package com.sss.subscriptionsharing.service;

import com.sss.subscriptionsharing.domain.user.Status;
import com.sss.subscriptionsharing.domain.user.User;
import com.sss.subscriptionsharing.exception.NoAuthorityException;
import com.sss.subscriptionsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(String loginId, String password, String name, String nickName,
                     String introduce, String email) {
        validateDuplicateId(loginId);
        validateDuplicateNickName(nickName);
        validateDuplicateEmail(email);

        User user = User.create(loginId, password, name, nickName, introduce, email);
        userRepository.save(user);

        return user;
    }

    public void validateDuplicateId(String loginId) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public void validateDuplicateNickName(String nickName) {
        Optional<User> findUser = userRepository.findByNickName(nickName);
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    public void validateDuplicateEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    @Transactional
    public void editInfo(Long userId, String loginId, String password, String name,
                         String nickName, String introduce, String email){
        validateDuplicateId(loginId);
        validateDuplicateNickName(nickName);
        validateDuplicateEmail(email);

        User user = userRepository.findById(userId).get();
        user.edit(loginId, password, name, nickName, introduce, email);
    }

    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Transactional
    public void withdrawal(Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public void validateAuthority(User user) {
        if (user.getStatus() == Status.SUSPENSION) {
            throw new NoAuthorityException("권한이 없습니다.");
        }
    }
}
