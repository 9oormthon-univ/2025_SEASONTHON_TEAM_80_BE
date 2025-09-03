package com.goormthon.backend.firstsori.domain.board.application.usecase;

import com.goormthon.backend.firstsori.domain.board.application.dto.BoardInfoResponse;
import com.goormthon.backend.firstsori.domain.board.application.mapper.BoardMapper;
import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.message.domain.service.GetMessageService;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;
import com.goormthon.backend.firstsori.domain.user.domain.service.GetUserService;
import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardUseCaseImpl implements BoardUseCase {

    private final GetUserService getUserService;

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
