package com.goormthon.backend.firstsori.domain.board.presentation.spec;

import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageListResponse;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageResponse;
import com.goormthon.backend.firstsori.global.common.response.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Board API", description = "메세지 게시판 관련 API")
public interface BoardControllerSpec {

    @Operation(summary = "메세지 상세 정보 조회", description = "단일 메세지의 상세 내용을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "메세지 ID를 찾을 수 없음")
    })
    @GetMapping("/{messageId}")
    com.goormthon.backend.firstsori.global.common.response.ApiResponse<MessageResponse> getMessage(
            @Parameter(
                    description = "조회할 메세지 ID",
                    example = "ff54b002-7f36-496e-9d8e-cf7dc41556d7"
            )
            @PathVariable UUID messageId    );

    @Operation(
            summary = "전체 메세지 목록 조회",
            description = "사용자 ID에 해당하는 게시판의 메세지 목록을 페이징하여 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json"
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "사용자 또는 게시판을 찾을 수 없음"
            )
    })
    @GetMapping
    com.goormthon.backend.firstsori.global.common.response.ApiResponse<PageResponse<MessageListResponse>> getMessages(
            @Parameter(
                    description = "메세지를 조회할 사용자 ID",
                    example = "fd1c199e-ae84-4cbd-9baa-e94da3d553d0"
            )
            @RequestParam UUID userId,

            @Parameter(
                    description = "페이징 정보",
                    example = "{ \"page\": 0, \"size\": 10, \"sort\": [\"createdDate,desc\"] }"
            )
            Pageable pageable
    );

}
