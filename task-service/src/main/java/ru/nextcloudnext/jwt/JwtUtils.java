package ru.nextcloudnext.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.exception.JwtCustomException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Long getIdFromAccessToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretAccessKey).parseClaimsJws(token).getBody().get("id", Long.class);
        } catch (IndexOutOfBoundsException exception) {
            throw new JwtCustomException(exception.getMessage());
        }
    }

    public Set<String> getRolesFromAccessToken(String token) {
        try {
            Set<String> roles = new HashSet<>();
            List<?> list = Jwts.parser().setSigningKey(secretAccessKey).parseClaimsJws(token).getBody().get("roles", List.class);
            for (Object o : list) {
                roles.add(o.toString());
            }
            return roles;
        } catch (IndexOutOfBoundsException exception) {
            throw new JwtCustomException(exception.getMessage());
        }
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
