package com.goormthon.backend.firstsori.domain.board.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class GetShareUriResponse {
    private UUID boardId;
    private String shareUri;
}


