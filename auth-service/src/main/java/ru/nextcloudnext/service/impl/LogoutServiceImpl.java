package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nextcloudnext.dto.response.LogoutResponseDTO;
import ru.nextcloudnext.exception.BadRequestException;
import ru.nextcloudnext.model.User;
import ru.nextcloudnext.repository.TokenSessionRepository;
import ru.nextcloudnext.repository.UserRepository;
import ru.nextcloudnext.service.LogoutService;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final UserRepository userRepository;
    private final TokenSessionRepository tokenSessionRepository;

    @Transactional
    @Override
    public LogoutResponseDTO logoutById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException(String.format("User not found with id '%d'", userId)));

        if (!tokenSessionRepository.existsByUser(user)) {
            throw new BadRequestException(String.format("Token session not found for userId '%d'", userId));
        }

        tokenSessionRepository.deleteAllByUser(user);
        return new LogoutResponseDTO(String.format("User with id '%d' is successfully logged off", user.getId()));
    }
}
