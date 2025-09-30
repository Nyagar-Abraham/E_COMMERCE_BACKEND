package org.abraham.user_service.exceptions;

public class FailedResetException extends RuntimeException {
    public FailedResetException(String message) {
        super(message);
    }
}
