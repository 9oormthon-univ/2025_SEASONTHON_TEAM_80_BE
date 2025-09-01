package com.goormthon.backend.firstsori.domain.message.domain.repository;

import com.goormthon.backend.firstsori.domain.message.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByMessageId(UUID messageId);

    @Query("SELECT m FROM Message m JOIN m.board b WHERE b.user.userId = :userId ORDER BY m.createdAt DESC")
    Page<Message> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Message m SET m.read = true WHERE m.messageId = :messageId AND m.read = false")
    int markReadIfUnread(@Param("messageId") UUID messageId);




}
