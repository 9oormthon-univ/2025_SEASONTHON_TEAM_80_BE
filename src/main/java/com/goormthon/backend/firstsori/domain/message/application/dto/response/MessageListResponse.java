package com.goormthon.backend.firstsori.domain.message.application.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record MessageListResponse(
        UUID messageId,
        String sender,
        String coverImageUrl,
        boolean read
) {
}
