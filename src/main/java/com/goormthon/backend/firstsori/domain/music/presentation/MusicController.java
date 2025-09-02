package com.goormthon.backend.firstsori.domain.music.presentation;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.GetSearchResultResponse;
import com.goormthon.backend.firstsori.domain.music.application.usecase.MusicUseCase;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/music")
@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicUseCase musicUseCase;

    // 키워드로 음악 검색
    @GetMapping("/search")
    public ApiResponse<GetSearchResultResponse> getSearchResult(
            @RequestParam String keyword) {
        GetSearchResultResponse response = musicUseCase.getSearchResult(keyword);
        return ApiResponse.ok(response);
    }
}
