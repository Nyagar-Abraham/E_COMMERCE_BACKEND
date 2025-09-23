package org.abraham.user_service.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//A configuration class to hold JWT-related properties like secret key, expiration time, and issuer.
@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProperties {
    private String secret;
    private int accessTokenExpiration;
    private int refreshTokenExpiration;

}
