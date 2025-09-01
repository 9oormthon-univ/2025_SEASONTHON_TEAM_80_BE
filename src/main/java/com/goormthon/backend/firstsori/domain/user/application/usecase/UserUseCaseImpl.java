package com.goormthon.backend.firstsori.domain.user.application.usecase;

import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.domain.user.domain.service.GetUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final GetUserService getUserService;

    @Transactional(readOnly = true)
    @Override
    public User findByUserId(UUID userId) {
        return getUserService.validateUserExists(userId);
    }


}
