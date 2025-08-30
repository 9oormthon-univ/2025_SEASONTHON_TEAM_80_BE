package com.goormthon.backend.firstsori.domain.message.domain.entity;

import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "messages")
@AllArgsConstructor
@SuperBuilder
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "message_id", nullable = false)
    private UUID messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean read = false;


}
