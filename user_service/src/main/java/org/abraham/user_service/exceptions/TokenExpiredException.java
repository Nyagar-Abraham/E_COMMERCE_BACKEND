package org.abraham.user_service.exceptions;

import java.text.MessageFormat;

public class TokenExpiredException extends RuntimeException {
    private static final String MESSAGE = "%s token expired";
    public TokenExpiredException(String message) {
        super(MESSAGE.formatted(message));
    }
}
