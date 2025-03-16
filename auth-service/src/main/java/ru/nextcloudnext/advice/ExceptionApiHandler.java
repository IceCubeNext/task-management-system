package ru.nextcloudnext.advice;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nextcloudnext.exception.BadRequestException;
import ru.nextcloudnext.exception.JwtCustomException;
import ru.nextcloudnext.exception.JwtRefreshExpiredException;
import ru.nextcloudnext.utility.Constants;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> onConstraintValidationException(
            ConstraintViolationException exception
    ) {
        final List<Violation> violations = exception.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return getViolationResponse(violations);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> onBadRequestException(BadRequestException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .reason("Incorrectly made request.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<ApiError> JwtCustomException(JwtCustomException exception) {
        log.error("Jwt custom exception.{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("Jwt has problem. Access denied.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> onExpiredJwtException(ExpiredJwtException exception) {
        log.error("Jwt access is expired. Access denied.{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("Jwt has problem (expired access Jwt). Please get new token.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(JwtRefreshExpiredException.class)
    public ResponseEntity<ApiError> JwtRefreshExpiredException(JwtRefreshExpiredException exception) {
        log.error("Jwt refresh is expired. Access denied.{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiError.builder()
                        .status(HttpStatus.FORBIDDEN)
                        .reason("Jwt has problem (expired refresh Jwt). Please login.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> onAuthenticationException(AuthenticationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("Jwt has problem. Access denied.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> throwableException(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        log.error(sw.toString());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .reason("Internal server error.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    private ResponseEntity<ApiError> getViolationResponse(List<Violation> violations) {
        for (Violation violation : violations) {
            log.error("error validation. {}: {}", violation.getFieldName(), violation.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(violations)
                        .reason("Incorrectly made request.")
                        .message("error validation.")
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }
}
