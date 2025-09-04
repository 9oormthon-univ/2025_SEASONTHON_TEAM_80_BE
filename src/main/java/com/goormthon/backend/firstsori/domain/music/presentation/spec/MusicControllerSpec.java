package com.goormthon.backend.firstsori.domain.music.presentation.spec;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.GetSearchResultResponse;
import com.goormthon.backend.firstsori.domain.music.application.dto.response.MusicChartResponse;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "음악 관련 API", description = "음악 검색 기능을 제공하는 API 입니다.")
public interface MusicControllerSpec {

    @Operation(
            summary = "음악 검색 API",
            description = "키워드를 바탕으로 음악을 검색하고 결과를 반환합니다."
    )
    ApiResponse<GetSearchResultResponse> getSearchResult(
            @RequestParam String keyword);

    @Operation(
            summary = "인기 음악 차트 조회 API",
            description = "저장이 많이 된 음악을 내림차순으로 10개 반환합니다."
    )
    @GetMapping("/popular-chart")
    ApiResponse<List<MusicChartResponse>> getPopularMusic(@RequestParam(defaultValue = "10") int topN);
}

