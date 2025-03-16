package ru.nextcloudnext.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.response.LogoutResponseDTO;
import ru.nextcloudnext.service.LogoutService;


@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
@Tag(name = "Авторизация", description = "Авторизация и аутентификация пользователей")
public class LogoutController {

    private final LogoutService logoutService;

    @Operation(
            summary = "Выход пользователя из системы",
            description = "Позволяет завершить работу пользователя"
    )
    @DeleteMapping(value = "/logout/{id}")
    public LogoutResponseDTO logout(
            @Parameter(description = "Идентификатор пользователя", required = true, example = "1")
            @PathVariable(name = "id") Long userId) {
        log.debug("User with id={} has logged out", userId);
        return logoutService.logoutById(userId);
    }
}
