package com.goormthon.backend.firstsori.domain.board.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateBoardResponse {
    private UUID boardId;
    private UUID userId;
    private String nickname;
    private String shareUri;
}


