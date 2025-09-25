package org.abraham.user_service.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
@Data
public class JwtProperties {
    private static final Logger log = LoggerFactory.getLogger(JwtProperties.class);

    private String secret;                 // Spring will map spring.jwt.secret
    private int accessTokenExpiration;     // spring.jwt.accessTokenExpiration
    private int refreshTokenExpiration;    // spring.jwt.refreshTokenExpiration

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(this.secret.getBytes());
    }
}

