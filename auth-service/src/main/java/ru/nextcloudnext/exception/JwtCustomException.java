package ru.nextcloudnext.exception;

public class JwtCustomException extends RuntimeException {
    public JwtCustomException(String message) {
        super(message);
    }
}
