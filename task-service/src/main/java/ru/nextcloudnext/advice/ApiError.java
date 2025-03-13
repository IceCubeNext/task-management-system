package ru.nextcloudnext.advice;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ApiError {
    private List<Violation> errors;
    private String reason;
    private String message;
    private HttpStatus status;
    private String timestamp;
}
