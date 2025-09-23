package org.abraham.user_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

//A class responsible for generating, parsing, and validating JWT tokens. It should contain methods for creating tokens (e.g., during user login), extracting claims, and verifying signatures.
@Component
public class JwtUtil {


    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(UserDetails userDetails) {
        // ... generate JWT with claims like username, roles, expiration

        return Jwts
                .builder()
                .claims(createClaims(userDetails))
//                .signWith()
                .compact();
    }

    public Claims extractAllClaims(String token) {
        // ... parse and extract claims
        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // ... validate signature, expiration, and user details
        return null;
    }

    private Map<String, Object> createClaims(UserDetails userDetails) {
        return Map.of(
                "subject", userDetails.getUsername(),
                "role", userDetails.getAuthorities()
        );
    }
}
