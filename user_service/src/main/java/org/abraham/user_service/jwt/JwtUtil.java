package org.abraham.user_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.abraham.user_service.entity.UserEntity;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

//A class responsible for generating, parsing, and validating JWT tokens. It should contain methods for creating tokens (e.g., during user login), extracting claims, and verifying signatures.
@Component
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(UserEntity user) {
        return generateToken(user, jwtProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(UserEntity user) {
        return generateToken(user, jwtProperties.getRefreshTokenExpiration());
    }

    private String generateToken(UserEntity user, int jwtExpirationMs) {
        var now = new Date();
        var expiry = new Date(now.getTime() + jwtExpirationMs * 60_000L);
        var claims = Jwts
                .claims()
                .subject(user.getId().toString())
                .add(createClaims(user))
                .issuedAt(new Date())
                .expiration(expiry)
                .build();
        return new Jwt(claims, jwtProperties.getSecretKey()).toString();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtProperties.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Jwt parseToken(String token) {
        try{
            var claims = extractAllClaims(token);
            return new Jwt(claims, jwtProperties.getSecretKey());
        }catch (JwtException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    private Map<String, Object> createClaims(UserEntity user) {
        return Map.of(
                "email", user.getEmail(),
                "username", user.getUsername(),
                "role", user.getRole()
        );
    }
}
