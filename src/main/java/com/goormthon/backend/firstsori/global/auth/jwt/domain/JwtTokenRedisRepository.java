package com.goormthon.backend.firstsori.global.auth.jwt.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface JwtTokenRedisRepository extends CrudRepository<JwtToken, String> {

    Optional<JwtToken> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

    Optional<JwtToken> findByUserId(UUID userId);


}
