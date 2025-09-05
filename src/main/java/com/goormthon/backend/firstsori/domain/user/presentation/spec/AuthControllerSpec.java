package com.goormthon.backend.firstsori.domain.user.presentation.spec;

import com.goormthon.backend.firstsori.global.auth.oauth2.domain.PrincipalDetails;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Tag(name = "인증 관련 API", description = "유저의 토큰 재발급, 로그아웃의 기능을 수행하는 API 입니다.")
public interface AuthControllerSpec {

    @Operation(
            summary = "토큰 재발급 API",
            description = "RefeshToken을 바탕으로 AccessToken을 재발급 할 수 있습니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(ref = "#/components/schemas/ApiResponseString")
                    )
            )
    })
    ApiResponse<String> reissue(
            HttpServletRequest request,
            HttpServletResponse response);

    @Operation(
            summary = "로그아웃 API",
            description = "쿠키가 존재하고, 카카오/구글을 통해 로그인한 회원에서 가능합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(ref = "#/components/schemas/ApiResponseString")
                    )
            )
    })
    ApiResponse<String> logout(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            HttpServletRequest request,
            HttpServletResponse response);

}
