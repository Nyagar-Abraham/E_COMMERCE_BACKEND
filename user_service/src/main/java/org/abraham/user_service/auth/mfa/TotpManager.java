package org.abraham.user_service.auth.mfa;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface TotpManager {
    String generateSecret();

    String generateCode(String secret, String email) throws QrGenerationException;

    Boolean verifyCode(String code, String secret);
}
