package com.goormthon.backend.firstsori.domain.user.domain.repository;

import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(UUID userId);

}
