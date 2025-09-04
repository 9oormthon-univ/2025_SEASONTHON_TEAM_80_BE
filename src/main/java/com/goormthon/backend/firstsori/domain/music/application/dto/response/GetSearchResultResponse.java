package com.goormthon.backend.firstsori.domain.music.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

// 음악 검색 결과 DTO
@Schema(name = "[GET] 음악 검색 response",
        description = "음악 검색 결과 응답")
public record GetSearchResultResponse(

        @Schema(description = "검색된 곡 정보 목록")
        List<SongData> searchResult
) {
}
