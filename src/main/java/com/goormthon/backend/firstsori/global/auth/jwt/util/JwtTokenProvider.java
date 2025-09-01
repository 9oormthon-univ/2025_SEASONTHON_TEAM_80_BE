package com.goormthon.backend.firstsori.global.auth.jwt.util;

import com.goormthon.backend.firstsori.global.auth.oauth2.domain.PrincipalDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

import static com.goormthon.backend.firstsori.global.auth.jwt.util.TokenNameUtil.ID_CLAIM;
import static com.goormthon.backend.firstsori.global.auth.jwt.util.TokenNameUtil.ROLE_CLAIM;

@Component
public class JwtTokenProvider {

    @Value("${firstsori.jwt.key}")
    private String key;

    @Value("${firstsori.jwt.access.expiration}")
    private Long accessTokenExpiration;

    @Value("${firstsori.jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    private SecretKey secretKey;


    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    // AccessToken 발급
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, accessTokenExpiration);
    }

    // RefreshToken 발급
    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, refreshTokenExpiration);
    }

    // 토큰 생성
    public String generateToken(Authentication authentication, long expireTime) {

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        // 인증에서 객체 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        /// 권한 리스트 추출
        Collection<? extends GrantedAuthority> collection = principalDetails.getAuthorities();

        // 유저의 권한 리스트를 String 형태로 변환
        List<String> authorities = collection.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        /// JWT 내용 생성 (userId, Role 포함)
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_CLAIM, principalDetails.getId());
        claims.put(ROLE_CLAIM, authorities);

        // 토큰 반환
        return Jwts.builder()
                .setSubject(String.valueOf(principalDetails.getId())) // 사용자 Id
                .setClaims(claims)
                .setIssuedAt(now)                                // 발급 시간
                .setExpiration(expiredDate)                      // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS512)   // 서명
                .compact();
    }


}
