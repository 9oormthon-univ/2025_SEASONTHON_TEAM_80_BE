package com.goormthon.backend.firstsori.global.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthExceptionHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 오류 메세지 작성
        ErrorCode errorCode = ErrorCode.INVALID_LOGIN;
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(new CustomException(errorCode))));

    }


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(new CustomException(errorCode))));

    }

}