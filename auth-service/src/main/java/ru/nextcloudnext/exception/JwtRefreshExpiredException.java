package ru.nextcloudnext.exception;

public class JwtRefreshExpiredException extends RuntimeException {
    public JwtRefreshExpiredException(String message) {
        super(message);
    }
}
