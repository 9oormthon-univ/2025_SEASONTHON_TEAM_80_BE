package com.goormthon.backend.firstsori.domain.message.presentation;

import com.goormthon.backend.firstsori.domain.message.application.dto.request.SaveMessageRequest;
import com.goormthon.backend.firstsori.domain.message.application.usecase.MessageUseCase;
import com.goormthon.backend.firstsori.domain.message.presentation.spec.MessageControllerSpec;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController implements MessageControllerSpec {

    private final MessageUseCase messageUseCase;

    @PostMapping
    public ApiResponse<String> sendMessage(
            @Valid @RequestBody SaveMessageRequest request) {
        messageUseCase.createMessage(request);
        return ApiResponse.ok("메세지 발행 성공");

    }
}
