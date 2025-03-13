package ru.nextcloudnext.controller;

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
public class LogoutController {

    private final LogoutService logoutService;

    @DeleteMapping(value = "/logout/{id}")
    public LogoutResponseDTO logout(@PathVariable(name = "id") Long userId) {
        log.debug("User with id={} has logged out", userId);
        return logoutService.logoutById(userId);
    }
}
