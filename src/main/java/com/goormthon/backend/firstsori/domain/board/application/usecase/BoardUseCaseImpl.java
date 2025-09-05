package com.goormthon.backend.firstsori.domain.board.application.usecase;

import com.goormthon.backend.firstsori.domain.board.application.dto.request.CreateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.request.UpdateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.CreateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.GetShareUriResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.BoardInfoResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.UpdateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.mapper.BoardMapper;
import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.board.domain.repository.BoardRepository;
import com.goormthon.backend.firstsori.domain.board.domain.service.GetBoardService;
import com.goormthon.backend.firstsori.domain.user.application.usecase.UserUseCase;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.domain.user.domain.repository.UserRepository;
import com.goormthon.backend.firstsori.global.auth.jwt.util.JwtTokenExtractor;
import com.goormthon.backend.firstsori.domain.user.domain.service.GetUserService;
import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardUseCaseImpl implements BoardUseCase {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserUseCase userUseCase;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final GetUserService getUserService;
    private final GetBoardService getBoardService;

    @Transactional
    @Override
    public CreateBoardResponse createBoard(CreateBoardRequest request, User user) {

        String nickname = request.getNickname();

        if (nickname == null || nickname.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        Board existingBoard = boardRepository.findByUser(user).orElse(null);
        if (existingBoard != null) {
            // 이미 존재하면 기존 보드 반환
            return new CreateBoardResponse(existingBoard.getBoardId(), user.getUserId(),existingBoard.getNickname(), existingBoard.getShareUri());
        }

        // 공유 URI 중복만 방지 (닉네임은 중복 허용)
        String shareUri = generateUniqueShareUri();

        Board board = Board.builder()
                .user(user)
                .nickname(nickname)
                .shareUri(shareUri)
                .build();

        Board saved = boardRepository.save(board);

        return CreateBoardResponse.builder()
                .boardId(saved.getBoardId())
                .userId(saved.getUser().getUserId())
                .nickname(saved.getNickname())
                .shareUri(saved.getShareUri())
                .build();
    }

    private String generateUniqueShareUri() {
        String candidate;
        do {
            candidate = RandomStringUtils.randomAlphanumeric(12);
        } while (boardRepository.existsByShareUri(candidate));
        return candidate;
    }

    private String extractTokenFromBearer(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }
        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return authorizationHeader;
    }

    @Transactional(readOnly = true)
    @Override
    public GetShareUriResponse getShareUriByUser(User user) {

        Board board = boardRepository.findByUser(user)
                .orElse(null);

        if (board == null) {
            return GetShareUriResponse.builder()
                    .boardId(null)
                    .shareUri(null)
                    .build();
        }

        return GetShareUriResponse.builder()
                .boardId(board.getBoardId())
                .shareUri(board.getShareUri())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public BoardInfoResponse getBoardInfo(String shareUri) {
        Board board = getBoardService.getBoardBySharedId(shareUri);

        return BoardMapper.toBoardInfoResponse(board.getNickname(), board.getUser().getProfileImage(), board.getMessageCount());
    }

    @Transactional
    @Override
    public UpdateBoardResponse updateBoard(UpdateBoardRequest request, User user) {
        // 사용자의 보드 조회
        Board board = boardRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 1) 사용자 프로필 이미지 먼저 반영 (보드로의 cascade 저장이 있어도 이후 닉네임이 최종 반영되도록 순서 조정)
        User savedUser = user;
        if (request.getProfileImage() != null) {
            user.update(null, null, request.getProfileImage());
            savedUser = userRepository.saveAndFlush(user);
        }

        // 2) 보드 닉네임 업데이트를 마지막에 수행해 최종값 보장
        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            boardRepository.updateNicknameByUser(user, request.getNickname());
        }

        // 3) 최신 보드 재조회하여 최신 값으로 응답
        Board savedBoard = boardRepository.findByUser(savedUser)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 응답 닉네임은 요청값 우선(부분 업데이트 시 최신 값 확정)
        String responseNickname = (request.getNickname() != null && !request.getNickname().isBlank())
                ? request.getNickname() : savedBoard.getNickname();

        return UpdateBoardResponse.builder()
                .boardId(savedBoard.getBoardId())
                .userId(savedUser.getUserId())
                .nickname(responseNickname)
                .profileImage(savedUser.getProfileImage())
                .shareUri(savedBoard.getShareUri())
                .build();
    }

}


