package com.goormthon.backend.firstsori.domain.board.application.usecase;

import com.goormthon.backend.firstsori.domain.board.application.dto.request.CreateBoardRequest;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.CreateBoardResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.GetShareUriResponse;
import com.goormthon.backend.firstsori.domain.board.application.dto.response.BoardInfoResponse;
import com.goormthon.backend.firstsori.domain.user.domain.entity.User;

import java.util.UUID;

public interface BoardUseCase {

    CreateBoardResponse createBoard(CreateBoardRequest request, User user);

    GetShareUriResponse getShareUriByUser(User user);

    BoardInfoResponse getBoardInfo(UUID userId);

}
