package com.goormthon.backend.firstsori.domain.message.presentation.spec;

import com.goormthon.backend.firstsori.domain.message.application.dto.request.SaveMessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Message API", description = "메세지 관련 API")
public interface MessageControllerSpec {

    @Operation(
            summary = "메시지 생성",
            description = "익명 사용자를 포함한 모든 사용자가 보드에 메시지를 게시합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 발행 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않거나, 필수 정보가 누락되었습니다."),
            @ApiResponse(responseCode = "404", description = "요청된 보드 ID를 찾을 수 없습니다.")
    })
    @PostMapping
    com.goormthon.backend.firstsori.global.common.response.ApiResponse<String> sendMessage(
            @RequestBody @Valid SaveMessageRequest request
    );
}
