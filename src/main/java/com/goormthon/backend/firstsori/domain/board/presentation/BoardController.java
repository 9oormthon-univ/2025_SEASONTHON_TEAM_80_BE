package com.goormthon.backend.firstsori.domain.board.presentation;

import com.goormthon.backend.firstsori.domain.board.application.dto.response.BoardInfoResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.request.CreateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.request.UpdateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.BoardPreviewResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.CreateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.GetShareUriResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.UpdateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.usecase.BoardUseCase;
import com.goormthon.backend.firstsori.domain.board.presentation.spec.BoardControllerSpec;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageListResponse;
import com.goormthon.backend.firstsori.domain.message.application.dto.response.MessageResponse;
import com.goormthon.backend.firstsori.domain.message.application.usecase.MessageUseCase;
import com.goormthon.backend.firstsori.global.auth.oauth2.domain.PrincipalDetails;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import com.goormthon.backend.firstsori.global.common.response.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            @AuthenticationPrincipal PrincipalDetails user,
            Pageable pageable) {
        PageResponse<MessageListResponse> messageListResponses = messageUseCase.getMessages(user.getId(), pageable);
        return ApiResponse.ok(messageListResponses);
    }

    // 외부인 접속 시 전체 메세지 앨범 커버만 조회
    @GetMapping("/shared/{shareUri}")
    public ApiResponse<List<BoardPreviewResponse>> getBoardMessages(@PathVariable String shareUri) {
        List<BoardPreviewResponse> response = messageUseCase.getMessagesByBoardShareUri(shareUri);
        return ApiResponse.ok(response);
    }

    // 보드 생성
    @PostMapping("/create")
    public ApiResponse<CreateBoardResponse> createBoard(
            @RequestBody CreateBoardRequest request,
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        CreateBoardResponse response = boardUseCase.createBoard(request, user.getUser());
        return ApiResponse.ok(response);
    }

    // 보드 공유 URI 조회
    @GetMapping("/share")
    public ApiResponse<GetShareUriResponse> getShareUri(
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        GetShareUriResponse response = boardUseCase.getShareUriByUser(user.getUser());
        return ApiResponse.ok(response);
    }


    // 보드 정보 반환
    @GetMapping("/info/{sharedUri}")
    public ApiResponse<BoardInfoResponse> getBoardInfo(@PathVariable String sharedUri) {
        BoardInfoResponse boardInfo=boardUseCase.getBoardInfo(sharedUri);
        return ApiResponse.ok(boardInfo);
    }

    // 보드 수정 (닉네임, 프로필 이미지)
    @PatchMapping("/update")
    public ApiResponse<UpdateBoardResponse> updateBoard(
            @RequestBody UpdateBoardRequest request,
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        UpdateBoardResponse response = boardUseCase.updateBoard(request, user.getUser());
        return ApiResponse.ok(response);
    }

}
