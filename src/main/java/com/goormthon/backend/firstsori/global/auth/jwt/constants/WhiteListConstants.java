package com.goormthon.backend.firstsori.global.auth.jwt.constants;

import java.util.List;

public class WhiteListConstants {

    public static final List<String> FILTER_WHITE_LIST = List.of(
            "/oauth/kakao/**",
            "/api/kakao/login",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/webjars/**"
    );

    public static final String[] SECURITY_WHITE_LIST = {
            "/oauth/kakao/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

}
