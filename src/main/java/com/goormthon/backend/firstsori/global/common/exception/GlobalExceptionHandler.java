package com.goormthon.backend.firstsori.global.common.exception;


import com.goormthon.backend.firstsori.global.common.response.ApiResponse;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

/**
 * 전역 예외 처리 핸들러
 * - @Hidden 은 스웨거에서 인식 되지 않도록 에러 수정용
 */
@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    /// 공통 처리 메서드
    private ApiResponse<CustomException> handleCustomException(CustomException customException) {
        return ApiResponse.fail(customException);
    }

    /// 예외 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class})
    public ApiResponse<CustomException> handleIllegalStateException(Exception e) {

        /// 메세지 바탕으로 예외 코드 검색
        ErrorCode errorCode = ErrorCode.fromMessage(e.getMessage());

        /// 해당 예외 코드로 예외 처리
        CustomException exception = new CustomException(errorCode, null);

        return handleCustomException(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class, NoResourceFoundException.class})
    public ApiResponse<CustomException> handleNoSuchException(Exception e) {

        /// 메세지 바탕으로 예외 코드 검색
        ErrorCode errorCode = ErrorCode.fromMessage(e.getMessage());

        /// 해당 예외 코드로 예외 처리
        CustomException exception = new CustomException(errorCode, null);

        return handleCustomException(exception);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<CustomException> handleException(Exception e) {

        /// 로그 발생
        log.error(e.getMessage(), e);

        /// 500 예외 코드 검색
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        /// 해당 예외 코드로 예외 처리
        CustomException exception = new CustomException(errorCode, null);

        return handleCustomException(exception);
    }


}
