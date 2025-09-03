package com.goormthon.backend.firstsori.domain.board.domain.service;

import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.board.domain.repository.BoardRepository;
import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GetBoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board getBoardBySharedId(String sharedId) {
        return boardRepository.findBySharedId(sharedId)
                .orElseThrow(() -> new CustomException(ErrorCode.MESSAGE_NOT_FOUND));
    }

}
