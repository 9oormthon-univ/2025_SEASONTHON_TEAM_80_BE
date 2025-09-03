package com.goormthon.backend.firstsori.domain.user.presentation;

import com.goormthon.backend.firstsori.domain.user.presentation.spec.AuthControllerSpec;
import com.goormthon.backend.firstsori.global.auth.jwt.service.JwtTokenUseCase;
import com.goormthon.backend.firstsori.global.auth.oauth2.domain.PrincipalDetails;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpec {

    private final JwtTokenUseCase tokenService;

    @PostMapping("/reissue")
    public ApiResponse<String> reissue(HttpServletRequest request, HttpServletResponse response) {

        /// 재발급 하기
        tokenService.reissueByRefreshToken(request, response);

        return ApiResponse.ok("재발급 완료");
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, HttpServletResponse response) {

        // 유저
        UUID userId = principalDetails.getUser().getUserId();

        // 리프레쉬 토큰 삭제하기
        tokenService.logout(userId, request, response);

        return ApiResponse.ok("로그아웃 완료");
    }
}
