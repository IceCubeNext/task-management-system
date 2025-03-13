package ru.nextcloudnext.advice;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nextcloudnext.exception.JwtCustomException;
import ru.nextcloudnext.exception.NotFoundException;
import ru.nextcloudnext.utility.Constants;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLIntegrityConstraintViolationException;
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

    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<ApiError> JwtCustomException(JwtCustomException exception) {
        log.error("Jwt custom exception." + exception.getMessage());
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
        log.error("Jwt access is expired. Access denied." + exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .reason("Jwt has problem (expired access Jwt). Please get new token.")
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
            log.error("error validation. " + violation.getFieldName() + ": " + violation.getMessage());
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentException(IllegalArgumentException exception) {
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

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiError> onSqlConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .status(HttpStatus.CONFLICT)
                        .reason("Integrity constraint has been violated.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .status(HttpStatus.CONFLICT)
                        .reason("dataIntegrityViolationException.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        for (Violation violation : violations) {
            log.error("error validation. " + violation.getFieldName() + ": " + violation.getMessage());
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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> missingRequestParametersException(MissingServletRequestParameterException exception) {
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFoundException(NotFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .reason("The required object was not found.")
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(Constants.ERROR_TIME_FORMATTER))
                        .build());
    }
}
