package ru.nextcloudnext.service;

import ru.nextcloudnext.dto.request.RefreshTokenRequestDTO;
import ru.nextcloudnext.dto.response.TokenResponseDTO;
import ru.nextcloudnext.model.TokenSession;
import ru.nextcloudnext.model.User;

public interface TokenSessionService {

    TokenResponseDTO updateTokens(RefreshTokenRequestDTO refreshTokenRequestDTO);

    String saveSession(User user);

    void deleteUserSessions(User user);

    TokenSession getTokenSessionByHash(String hash);

    TokenSession updateTokenSession(TokenSession tokenSession);
}
