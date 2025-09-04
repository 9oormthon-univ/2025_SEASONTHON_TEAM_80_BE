package com.goormthon.backend.firstsori.domain.board.application.usecase;

import com.goormthon.backend.firstsori.domain.board.application.dto.request.CreateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.CreateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.GetShareUriResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.BoardInfoResponse;

import java.util.UUID;

public interface BoardUseCase {

    CreateBoardResponse createBoard(CreateBoardRequest request, String bearerToken);

    GetShareUriResponse getShareUriByUser(String bearerToken);

    BoardInfoResponse getBoardInfo(UUID userId);

}
