package com.goormthon.backend.firstsori.domain.music.application.dto.response;

import java.util.List;

public record GetSearchResultResponse(
        List<SongData> searchResult
) {
}
