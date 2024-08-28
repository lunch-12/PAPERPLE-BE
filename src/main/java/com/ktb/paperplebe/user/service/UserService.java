package com.ktb.paperplebe.user.service;

import com.ktb.paperplebe.user.dto.UserInfoResponse;
import com.ktb.paperplebe.user.entity.User;
import com.ktb.paperplebe.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid user ID: " + id));
    }

    public UserInfoResponse getUserInfo(Long id) {
        User user = findById(id);
        return UserInfoResponse.of(user);
    }

    @Transactional
    public UserInfoResponse updateNickname(Long userId, String nickname) {
        User user = findById(userId);

        validateDuplicatedNickname(nickname);

        user.updateNickname(nickname);
        return UserInfoResponse.of(user);
    }

    private void validateDuplicatedNickname(String nickname) {
        if (existsByNickname(nickname)) {
            throw new RuntimeException(nickname + "은 이미 사용 중인 닉네임입니다.");
        }
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
