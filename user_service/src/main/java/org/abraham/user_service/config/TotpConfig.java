package org.abraham.user_service.config;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TotpConfig {
    @Bean
    @ConditionalOnMissingBean
    public SecretGenerator secretGenerator() {
        return new DefaultSecretGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public QrGenerator qrGenerator() {
        return new ZxingPngQrGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public CodeGenerator codeGenerator() {
        return new DefaultCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public CodeVerifier codeVerifier() {
        var systemTimeProvider = new SystemTimeProvider();
        var codeGenerator = new DefaultCodeGenerator();
        return new DefaultCodeVerifier(codeGenerator, systemTimeProvider);
    }

}
