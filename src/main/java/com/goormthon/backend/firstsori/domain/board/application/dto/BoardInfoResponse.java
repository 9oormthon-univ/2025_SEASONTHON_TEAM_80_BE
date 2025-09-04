package com.goormthon.backend.firstsori.domain.board.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

// 보드 정보 응답 DTO
@Builder
@Schema(description = "보드 정보 및 소유자 프로필 응답")
public record BoardInfoResponse(
        @Schema(description = "보드 소유자의 닉네임")
        String name,

        @Schema(description = "보드 소유자의 프로필 이미지 URL")
        String profileImage,

        @Schema(description = "보드에 등록된 메시지 수")
        Integer messageCount) {
}
