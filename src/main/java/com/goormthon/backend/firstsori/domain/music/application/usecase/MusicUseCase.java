package com.goormthon.backend.firstsori.domain.music.application.usecase;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.GetSearchResultResponse;
import com.goormthon.backend.firstsori.domain.music.application.dto.response.MusicChartResponse;

import java.util.List;

public interface MusicUseCase {

    GetSearchResultResponse getSearchResult(String keyword);

    List<MusicChartResponse> getPopularMusic(int topN);

}
