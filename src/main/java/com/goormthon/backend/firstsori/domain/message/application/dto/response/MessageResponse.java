package com.goormthon.backend.firstsori.domain.message.application.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageResponse(
    UUID messageId,
    String senderName,
    String content,

    UUID songId,
    String songName,
    String artist,
    String coverImageUrl,
    String songUrl
) {
}
