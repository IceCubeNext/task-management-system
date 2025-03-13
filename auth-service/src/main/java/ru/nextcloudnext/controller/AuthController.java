package ru.nextcloudnext.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nextcloudnext.dto.request.SignInRequestDTO;
import ru.nextcloudnext.dto.request.SignUpRequestDTO;
import ru.nextcloudnext.dto.response.AuthResponseDTO;
import ru.nextcloudnext.service.AuthService;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    public AuthResponseDTO signIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        log.debug("Try to sign-in by login: {}", signInRequestDTO.getLogin());
        return authService.signIn(signInRequestDTO.getLogin(), signInRequestDTO.getPassword());
    }

    @PostMapping(value = "/sign-up")
    public AuthResponseDTO signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        log.debug("Try to sign-up by login: {}", signUpRequestDTO.getLogin());
        return authService.signUp(
                signUpRequestDTO.getLogin(),
                signUpRequestDTO.getPassword(),
                signUpRequestDTO.getFirstName(),
                signUpRequestDTO.getLastName(),
                signUpRequestDTO.getPatronymic()
        );
    }
}
