package com.goormthon.backend.firstsori.domain.board.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardRequest {

    private String nickname;
    private String profileImage;
}
