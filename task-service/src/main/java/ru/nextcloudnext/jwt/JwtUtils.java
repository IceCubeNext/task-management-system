package ru.nextcloudnext.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.exception.JwtCustomException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
@Component
public class JwtUtils {

    @Value("${security.jwt.token.secret-access-key}")
    private String secretAccessKey;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @PostConstruct
    protected void init() {
        secretAccessKey = Base64.getEncoder().encodeToString(secretAccessKey.getBytes());
    }

    public String getUsernameFromAccessToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretAccessKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Nullable
    public String resolveToken(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader(AUTHORIZATION);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(BEARER_PREFIX.length());
            }
            return null;
        } catch (IndexOutOfBoundsException exception) {
            throw new JwtCustomException(exception.getMessage());
        }
    }

    public void validateAccessToken(String token) {
        validateToken(token, secretAccessKey);
    }

    private void validateToken(String token, String secretKey) {
        Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
