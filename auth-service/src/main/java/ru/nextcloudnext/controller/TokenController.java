package ru.nextcloudnext.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.request.RefreshTokenRequestDTO;
import ru.nextcloudnext.dto.response.TokenResponseDTO;
import ru.nextcloudnext.service.TokenSessionService;

import javax.validation.Valid;


@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/token")
public class TokenController {

    private final TokenSessionService tokenSessionService;

    @PostMapping(value = "/refresh")
    public TokenResponseDTO updateTokens(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) {
        log.debug("Update token {}", refreshTokenRequestDTO);
        return tokenSessionService.updateTokens(refreshTokenRequestDTO);
    }
}
