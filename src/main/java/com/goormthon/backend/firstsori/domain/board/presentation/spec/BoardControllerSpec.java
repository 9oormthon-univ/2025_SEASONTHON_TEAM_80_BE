package com.goormthon.backend.firstsori.domain.board.presentation.spec;

import com.goormthon.backend.firstsori.domain.board.application.dto.BoardInfoResponse;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageListResponse;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageResponse;
import com.goormthon.backend.firstsori.global.auth.oauth2.domain.PrincipalDetails;
import com.goormthon.backend.firstsori.global.common.response.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

            @AuthenticationPrincipal PrincipalDetails user,
            @Parameter(
                    description = "페이징 정보",
                    example = "{ \"page\": 0, \"size\": 10, \"sort\": [\"desc\"] }"
            )
            Pageable pageable
    );

    @Operation(
            summary = "보드 정보 반환",
            description = "인증된 사용자의 보드 정보(닉네임, 프로필 사진, 총 메시지 수 등)를 반환합니다. 토큰이 필요합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "보드 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 부재 또는 유효하지 않은 토큰)"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/info")
    com.goormthon.backend.firstsori.global.common.response.ApiResponse<BoardInfoResponse> getBoardInfo(
            @AuthenticationPrincipal PrincipalDetails user
            );

}
