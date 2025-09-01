    package com.goormthon.backend.firstsori.global.auth.jwt.filter;

    import com.goormthon.backend.firstsori.global.auth.jwt.exception.JwtAuthenticationException;
    import com.goormthon.backend.firstsori.global.auth.jwt.util.JwtTokenExtractor;
    import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    import java.util.Optional;

    import static com.goormthon.backend.firstsori.global.common.exception.ErrorCode.*;


    @Slf4j
    @Component
    @RequiredArgsConstructor
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtTokenExtractor extractor;
        private final JwtAuthenticationFailureHandler failureHandler;
        private final RequestMatcherHolder requestMatcherHolder;

        public final static String JWT_ERROR = "jwtError";

        // doFilterInternal
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            // OPTIONS 필터에서 타지않도록 넣는다.
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }

            //
            boolean isAnonymousAllowed = requestMatcherHolder.getRequestMatchersByMinRole(null)
                    .matches(request);

            // 토큰 추출
            try {
                Optional<String> token = extractor.extractAccessToken(request);

                if (isAnonymousAllowed) {
                    // anonymous 허용: 토큰 있으면 인증, 없으면 anonymous로 통과
                    if (token.isPresent()) {
                        String accessToken = token.get();
                        if (!extractor.validateToken(accessToken)) {
                            request.setAttribute(JWT_ERROR, INVALID_TOKEN);
                            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN.getMessage());
                        }
                        if (extractor.isExpired(accessToken)) {
                            request.setAttribute(JWT_ERROR, TOKEN_EXPIRED);
                            throw new JwtAuthenticationException(TOKEN_EXPIRED.getMessage());
                        }
                        var authentication = extractor.getAuthentication(accessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    filterChain.doFilter(request, response);
                    return;
                }

                // 토큰 검증
                // 비어있는 지
                if (token.isEmpty()) {
                    request.setAttribute(JWT_ERROR, TOKEN_NOT_FOUND);
                    throw new JwtAuthenticationException(TOKEN_NOT_FOUND.getMessage());
                }

                String accessToken = token.get();

                // 타당한지
                if (!extractor.validateToken(accessToken)) {
                    request.setAttribute(JWT_ERROR, INVALID_TOKEN);
                    throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN.getMessage());
                }

                // 만료가 안되었는지
                if (extractor.isExpired(accessToken)) {
                    request.setAttribute(JWT_ERROR, TOKEN_EXPIRED);
                    throw new JwtAuthenticationException(TOKEN_EXPIRED.getMessage());
                }

                // 권한 생성하기
                var authentication = extractor.getAuthentication(token.get());

                /// 시큐리티 홀더에 해당 멤버 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);

            } catch (JwtAuthenticationException ex) {

                // 실패 핸들러 호출, 응답 생성 진행
                failureHandler.commence(request, response, ex);
            }
        }

        //TODO 여기 수정 필요
        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {

            /// 카카오 로그인은 필터 타지 않도록
            if (request.getRequestURI().startsWith("/api/v1/auth/kakao")) {
                return true; // JWT 필터 건너뜀
            }

            return false;

        }

    }
