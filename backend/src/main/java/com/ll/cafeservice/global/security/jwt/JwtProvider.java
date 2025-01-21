package com.ll.cafeservice.global.security.jwt;

import com.ll.cafeservice.global.security.exception.CustomJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Value("${JWT_VALIDITY_IN_MS}")
    private long validityInMilliseconds;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String resolveTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_key".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }



    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new CustomJwtException("토큰이 만료되었습니다.");
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new CustomJwtException("서명이 올바르지 않습니다.");
        } catch (Exception e) {
            throw new CustomJwtException("유효하지 않은 토큰입니다.");
        }
    }
}