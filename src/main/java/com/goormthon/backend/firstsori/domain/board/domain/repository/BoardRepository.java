package com.goormthon.backend.firstsori.domain.board.domain.repository;

import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    Optional<Board> findByShareUri(String shareUri);

    Optional<Board> findByUser(User user);

    boolean existsByShareUri(String shareUri);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Board b set b.nickname = :nickname where b.user = :user")
    int updateNicknameByUser(@Param("user") User user, @Param("nickname") String nickname);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Board b set b.messageCount = b.messageCount + :delta where b.boardId = :boardId")
    int incrementMessageCountById(@Param("boardId") UUID boardId, @Param("delta") int delta);
}
