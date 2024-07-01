package com.bachtx.wibucommon.utils;

import com.bachtx.wibucommon.constant.SecurityConstant;
import com.bachtx.wibucommon.exceptions.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Setter
public class JwtUtil {
    private SecretKey secretKey;
    private Long validity;

    public JwtUtil(String hashSecret, Long validity) {
        this.validity = validity;
        byte[] keyBytes = Base64.getDecoder().decode(hashSecret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(extractToken(token)).getPayload();
    }

    private String extractToken(String token) {
        if (token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            return token.substring(SecurityConstant.TOKEN_PREFIX.length());
        }
        return token;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(getAllClaimsFromToken(extractToken(token)));
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parse(extractToken(token));
            return true;
        } catch (MalformedJwtException ex) {
            throw new InvalidTokenException("Token invalid", ex);
        } catch (ExpiredJwtException ex) {
            throw new InvalidTokenException("Token expired", ex);
        } catch (UnsupportedJwtException ex) {
            throw new InvalidTokenException("Token unsupported", ex);
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("Token's claim is empty", ex);
        }
    }

    public String generateToken(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        return handleGenerateToken(claims, username);
    }

    private String handleGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + this.validity * 1000)
                )
                .signWith(this.secretKey)
                .compact();
    }
}
