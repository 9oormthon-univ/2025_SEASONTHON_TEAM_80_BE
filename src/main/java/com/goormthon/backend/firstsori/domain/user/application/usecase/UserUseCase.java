package com.goormthon.backend.firstsori.domain.user.application.usecase;

import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.domain.user.domain.entity.enums.Provider;

import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    User saveUser(User user);

    void updateUser(User user);

    boolean checkExistingById(UUID userId);

    Optional<User> loadUserById(UUID userId);

    boolean existsByEmail(String email);

    Optional<User> loadUserBySocialAndSocialId(Provider social, String socialId);

}
