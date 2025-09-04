package com.goormthon.backend.firstsori.domain.board.domain.repository;

import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
    Optional<Board> findBySharedId(String sharedId);

    Optional<Board> findByUser(User user);

    boolean existsByShareUri(String shareUri);
}
