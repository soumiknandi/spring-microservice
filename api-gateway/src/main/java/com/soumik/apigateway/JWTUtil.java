package com.soumik.apigateway;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    Logger log = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;
    private final String AUTH_HEADER = "Authorization";
    private final String AUTH_HEADER_PREFIX = "Bearer";


    @Getter
    @Setter
    public String error;

    public String getTokenFromHeader(ServerHttpRequest serverHttpRequest) {

        HttpHeaders headers = serverHttpRequest.getHeaders();

        if (headers.containsKey(AUTH_HEADER)) {
            String token = headers.getOrEmpty(AUTH_HEADER).get(0);
            if (token != null && token.startsWith(AUTH_HEADER_PREFIX)) {
                return token.substring(AUTH_HEADER_PREFIX.length()).trim();
            }
        }
        return null;
    }


    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);

        if (claims == null) return true;
        else return claims.getExpiration().before(new Date());

    }


    public Claims getClaimsFromToken(String token) {

        try {
            log.info("Token :: " + token);
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            setError("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            setError("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            setError("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            setError("JWT token compact of handler are invalid");
        } catch (JwtException ex) {
            log.info(ex.getMessage());
            setError("Invalid JWT signature");
        }
        return null;
    }

    private Key getSignInKey() {
        byte[] decode = Base64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(decode);
    }
}
