package com.goormthon.backend.firstsori.global.auth.jwt;

import com.goormthon.backend.firstsori.global.auth.jwt.constants.JWTConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && String.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
                                  @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Object tokenTypeObj = request.getAttribute(JWTConstants.TOKEN_TYPE);
        // 토큰 검증
        if (tokenTypeObj != JwtValidationType.VALID_ACCESS
                && tokenTypeObj != JwtValidationType.VALID_REFRESH) {
            throw new RuntimeException(
                    String.format("USER_ID를 가져오지 못했습니다. (%s - %s)", parameter.getClass(), parameter.getMethod()));
        }

        Object userIdObj = request.getAttribute(JWTConstants.USER_ID);
        if (userIdObj == null) {
            throw new RuntimeException(
                    String.format("USER_ID를 가져오지 못했습니다. (%s - %s)", parameter.getClass(), parameter.getMethod()));
        }

        try {
            return UUID.fromString(userIdObj.toString());  // UUID 객체로 반환
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    String.format("USER_ID를 UUID로 변환할 수 없습니다. (%s - %s)", parameter.getClass(), parameter.getMethod()));
        }
    }
}
