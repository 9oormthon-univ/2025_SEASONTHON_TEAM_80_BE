package com.goormthon.backend.firstsori.domain.board.application.usecase;

import com.goormthon.backend.firstsori.domain.board.application.dto.BoardInfoResponse;

import java.util.UUID;

public interface BoardUseCase {

    BoardInfoResponse getBoardInfo(UUID userId);

}
