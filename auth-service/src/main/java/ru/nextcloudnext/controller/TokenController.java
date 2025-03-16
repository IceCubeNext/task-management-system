package ru.nextcloudnext.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.request.RefreshTokenRequestDTO;
import ru.nextcloudnext.dto.response.TokenResponseDTO;
import ru.nextcloudnext.service.TokenSessionService;


@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/token")
@Tag(name = "Обновление токенов", description = "Выдает новую пару токенов")
public class TokenController {

    private final TokenSessionService tokenSessionService;

    @Operation(
            summary = "Выдача новой пары токенов",
            description = "Выдает новую пару токенов в случае если валиден refreshToken"
    )
    @PostMapping(value = "/refresh")
    public TokenResponseDTO updateTokens(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) {
        log.debug("Update token {}", refreshTokenRequestDTO);
        return tokenSessionService.updateTokens(refreshTokenRequestDTO);
    }
}
