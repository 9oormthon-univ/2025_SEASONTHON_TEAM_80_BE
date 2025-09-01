package com.goormthon.backend.firstsori.domain.user.application.usecase;

import com.goormthon.backend.firstsori.domain.user.domain.entity.User;

import java.util.UUID;

public interface UserUseCase {

    User findByUserId(UUID userId);
}
