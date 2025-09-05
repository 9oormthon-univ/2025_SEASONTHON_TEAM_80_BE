package com.goormthon.backend.firstsori.domain.board.application.mapper;

import com.goormthon.backend.firstsori.domain.board.application.dto.response.BoardInfoResponse;

import java.time.LocalDateTime;

public class BoardMapper {

    public static BoardInfoResponse toBoardInfoResponse(String name,String profileImage,Integer messageCount) {
        return BoardInfoResponse.builder()
                .name(name)
                .profileImage(profileImage)
                .messageCount(messageCount)
                .serverTime(LocalDateTime.now())
                .build();
    }
}
