package ru.nextcloudnext.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nextcloudnext.dto.request.RefreshTokenRequestDTO;
import ru.nextcloudnext.dto.response.TokenResponseDTO;
import ru.nextcloudnext.exception.JwtCustomException;
import ru.nextcloudnext.jwt.JwtUtils;
import ru.nextcloudnext.model.TokenSession;
import ru.nextcloudnext.model.User;
import ru.nextcloudnext.repository.TokenSessionRepository;
import ru.nextcloudnext.service.TokenSessionService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenSessionServiceImpl implements TokenSessionService {

    private final TokenSessionRepository tokenSessionRepository;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public TokenResponseDTO updateTokens(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String refreshToken = refreshTokenRequestDTO.getRefreshToken();
        jwtUtils.validateRefreshToken(refreshToken);

        String hash = jwtUtils.getHashFromRefreshToken(refreshToken);
        TokenSession tokenSession = getTokenSessionByHash(hash);
        User user = tokenSession.getUser();

        tokenSession = updateTokenSession(tokenSession);

        String accessToken = jwtUtils.generateAccessToken(user, tokenSession.getSessionHash());
        refreshToken = jwtUtils.generateRefreshToken(user.getLogin(), tokenSession.getSessionHash());

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public String saveSession(User user) {
        String hash = generateSessionHash();

        TokenSession tokenSession = new TokenSession();
        tokenSession.setUser(user);
        tokenSession.setSessionHash(hash);
        tokenSessionRepository.save(tokenSession);

        return hash;
    }

    @Override
    @Transactional
    public void deleteUserSessions(User user) {
        tokenSessionRepository.deleteAllByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenSession getTokenSessionByHash(String hash) {
        Optional<TokenSession> refreshTokenSession = tokenSessionRepository.findBySessionHash(hash);
        return refreshTokenSession.orElseThrow(() -> new JwtCustomException("Refresh token is invalid. It's occurred while function getTokenSessionByHash works "));
    }

    @Override
    @Transactional
    public TokenSession updateTokenSession(TokenSession tokenSession) {
        tokenSession.setSessionHash(generateSessionHash());
        tokenSession = tokenSessionRepository.save(tokenSession);
        return tokenSession;
    }

    private String generateSessionHash() {
        return UUID.randomUUID().toString();
    }
}
