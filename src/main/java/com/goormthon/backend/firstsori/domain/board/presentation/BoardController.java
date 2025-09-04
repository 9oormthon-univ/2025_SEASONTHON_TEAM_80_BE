package com.goormthon.backend.firstsori.domain.board.presentation;

import com.goormthon.backend.firstsori.domain.board.application.dto.request.CreateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.CreateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.GetShareUriResponse;
import com.goormthon.backend.firstsori.domain.board.application.usecase.BoardUseCase;
import com.goormthon.backend.firstsori.domain.board.presentation.spec.BoardControllerSpec;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageListResponse;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageResponse;
import com.goormthon.backend.firstsori.domain.message.application.usecase.MessageUseCase;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.global.auth.oauth2.domain.PrincipalDetails;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import com.goormthon.backend.firstsori.global.common.response.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController implements BoardControllerSpec {

    private final MessageUseCase messageUseCase;
    private final BoardUseCase boardUseCase;

    // 개별 메세지 조회
    @GetMapping("/{messageId}")
    public ApiResponse<MessageResponse> getMessage(@PathVariable UUID messageId) {
        MessageResponse messageResponse = messageUseCase.getMessage(messageId);
        return ApiResponse.ok(messageResponse);
    }

    // 전체 메세지 리스트 정보 조회
    @GetMapping
    public ApiResponse<PageResponse<MessageListResponse>> getMessages(
            @RequestParam UUID userId,
            Pageable pageable) {
        PageResponse<MessageListResponse> messageListResponses = messageUseCase.getMessages(userId, pageable);
        return ApiResponse.ok(messageListResponses);
    }

    // 보드 생성
    @PostMapping("/create")
    public ApiResponse<CreateBoardResponse> createBoard(
            @RequestBody CreateBoardRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        CreateBoardResponse response = boardUseCase.createBoard(request, authorization);
        return ApiResponse.ok(response);
    }

    // 보드 공유 URI 조회
    @GetMapping("/share")
    public ApiResponse<GetShareUriResponse> getShareUri(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        GetShareUriResponse response = boardUseCase.getShareUriByUser(authorization);
        return ApiResponse.ok(response);
    }


    // 보드 정보 반환
    @GetMapping("/info")
    public ApiResponse<BoardInfoResponse> getBoardInfo(@AuthenticationPrincipal PrincipalDetails user) {
        BoardInfoResponse boardInfo=boardUseCase.getBoardInfo(user.getId());
        return ApiResponse.ok(boardInfo);
    }

}
