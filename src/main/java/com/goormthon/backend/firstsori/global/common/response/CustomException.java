package com.goormthon.backend.firstsori.global.common.response;

import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * 응답을 하는 커스텀 예외 클래스입니다.
 * ErrorCode와 필드별 에러 정보를 함께 담아, 일관된 에러 응답을 제공합니다.
 */

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final int errorCode;

    private final String errorMessage;

}

