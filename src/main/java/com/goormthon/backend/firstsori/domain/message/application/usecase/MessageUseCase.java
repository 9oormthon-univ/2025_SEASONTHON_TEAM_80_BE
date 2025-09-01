package com.goormthon.backend.firstsori.domain.message.application.usecase;

import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageListResponse;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageResponse;
import com.goormthon.backend.firstsori.global.common.response.page.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageUseCase {

    MessageResponse getMessage(UUID messageId);

    PageResponse<MessageListResponse> getMessages(UUID userId, Pageable pageable); // 페이지 네이션 필요?

}
