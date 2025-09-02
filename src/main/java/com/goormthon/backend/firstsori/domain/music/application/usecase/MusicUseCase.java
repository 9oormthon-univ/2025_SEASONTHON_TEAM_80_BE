package com.goormthon.backend.firstsori.domain.music.application.usecase;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.GetSearchResultResponse;

public interface MusicUseCase {

    GetSearchResultResponse getSearchResult(String keyword);

}
