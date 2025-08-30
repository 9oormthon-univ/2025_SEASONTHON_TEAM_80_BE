package com.goormthon.backend.firstsori.global.common.response;

import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 응답을 하는 커스텀 예외 클래스입니다.
 * ErrorCode와 필드별 에러 정보를 함께 담아, 일관된 에러 응답을 제공합니다.
 */

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}

