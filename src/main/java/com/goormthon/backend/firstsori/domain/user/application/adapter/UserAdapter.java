package com.goormthon.backend.firstsori.domain.user.application.adapter;

import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.domain.user.domain.entity.UserPort;
import com.goormthon.backend.firstsori.domain.user.domain.entity.enums.Provider;
import com.goormthon.backend.firstsori.domain.user.domain.repository.UserRepository;
import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        /// 조회
        var entity = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        /// 수정사항 db에 반영
        entity.update(user.getName(), user.getEmail(), user.getProfileImage());
    }

    @Override
    public boolean checkExistingById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public Optional<User> loadUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<User> userJpaEntity = userRepository.findByEmail(email);
        return userJpaEntity.isPresent();
    }

    @Override
    public Optional<User> loadUserBySocialAndSocialId(Provider social, String socialId) {
        return userRepository.findByProviderAndSocialId(social, socialId);
    }

}
