package com.goormthon.backend.firstsori.domain.board.application.usecase;

import com.goormthon.backend.firstsori.domain.board.application.dto.request.CreateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.CreateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.GetShareUriResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.BoardInfoResponse;
import com.goormthon.backend.firstsori.domain.board.application.mapper.BoardMapper;
import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.board.domain.repository.BoardRepository;
import com.goormthon.backend.firstsori.domain.user.application.usecase.UserUseCase;
import com.goormthon.backend.firstsori.domain.message.domain.service.GetMessageService;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.global.auth.jwt.util.JwtTokenExtractor;
import com.goormthon.backend.firstsori.domain.user.domain.service.GetUserService;
import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardUseCaseImpl implements BoardUseCase {

    private final BoardRepository boardRepository;
    private final UserUseCase userUseCase;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final GetUserService getUserService;

    @Transactional
    @Override
    public CreateBoardResponse createBoard(CreateBoardRequest request, String bearerToken) {

        String nickname = request.getNickname();
        String token = extractTokenFromBearer(bearerToken);

        if (nickname == null || nickname.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        if (token == null || token.isBlank()) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }

        if (!jwtTokenExtractor.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        UUID userId = UUID.fromString(jwtTokenExtractor.getId(token));
        User user = userUseCase.findByUserId(userId);

        boardRepository.findByUser(user).ifPresent(b -> { throw new CustomException(ErrorCode.BAD_REQUEST); });

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
    public GetShareUriResponse getShareUriByUser(String bearerToken) {
        String token = extractTokenFromBearer(bearerToken);

        if (token == null || token.isBlank()) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }

        if (!jwtTokenExtractor.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        UUID userId = UUID.fromString(jwtTokenExtractor.getId(token));
        User user = userUseCase.findByUserId(userId);

        Board board = boardRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        return GetShareUriResponse.builder()
                .boardId(board.getBoardId())
                .shareUri(board.getShareUri())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public BoardInfoResponse getBoardInfo(UUID userId) {
        User user = Optional.ofNullable(getUserService.validateUserExists(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 보드랑 연결후에 작동 (혜연님 코드)
        Board board = Optional.ofNullable(user.getBoard())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        return BoardMapper.toBoardInfoResponse(user.getName(), user.getProfileImage(), board.getMessageCount());
    }

}


