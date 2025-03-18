package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.dto.response.AuthResponseDTO;
import ru.nextcloudnext.exception.BadRequestException;
import ru.nextcloudnext.jwt.JwtUtils;
import ru.nextcloudnext.model.User;
import ru.nextcloudnext.model.UserStatus;
import ru.nextcloudnext.repository.RoleRepository;
import ru.nextcloudnext.repository.UserRepository;
import ru.nextcloudnext.service.AuthService;
import ru.nextcloudnext.service.TokenSessionService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenSessionService tokenSessionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponseDTO signIn(String login, char[] password) {
        if (!StringUtils.hasText(login) || password.length == 0) {
            throw new BadRequestException("Указаны некорректные данные");
        }
        User user = userRepository
                .findUserByLogin(login)
                .orElseThrow(() -> new BadRequestException(String.format("Пользователь '%s' не существует", login)));

        if (user.getUserStatus() == UserStatus.NOT_ACTIVE) {
            throw new BadRequestException(String.format("Пользователь '%s' заблокирован", user.getLogin()));
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        String.valueOf(password)
                )
        );

        tokenSessionService.deleteUserSessions(user);
        String sessionHash = tokenSessionService.saveSession(user);

        String accessToken = jwtUtils.generateAccessToken(user, sessionHash);
        String refreshToken = jwtUtils.generateRefreshToken(user.getLogin(), sessionHash);

        return AuthResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .patronymic(user.getPatronymic())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public AuthResponseDTO signUp(String login, char[] password, String firstName, String lastName, String patronymic) {
        if (!StringUtils.hasText(login) || password.length == 0) {
            throw new BadRequestException("Указаны некорректные данные");
        }
        if (userRepository.findUserByLogin(login).isPresent()) {
            throw new BadRequestException(String.format("Пользователь '%s' уже существует", login));
        }

        User user = new User();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setPatronymic(patronymic);
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(String.valueOf(password)));
        user.setRoles(Set.of(roleRepository.getRoleById(1L)));
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        tokenSessionService.deleteUserSessions(user);
        String sessionHash = tokenSessionService.saveSession(user);

        String accessToken = jwtUtils.generateAccessToken(user, sessionHash);
        String refreshToken = jwtUtils.generateRefreshToken(user.getLogin(), sessionHash);

        return AuthResponseDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .patronymic(user.getPatronymic())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
