package com.goormthon.backend.firstsori.domain.board.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BoardPreviewResponse {
    private UUID messageId;
    private UUID musicId;
    private String musicCoverUrl;
}
