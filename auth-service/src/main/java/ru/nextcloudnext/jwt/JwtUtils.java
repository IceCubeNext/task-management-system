package ru.nextcloudnext.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.nextcloudnext.exception.JwtCustomException;
import ru.nextcloudnext.exception.JwtRefreshExpiredException;
import ru.nextcloudnext.model.User;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * @author Vladimir F. 10.01.2023
 */

@Slf4j
@Component
public class JwtUtils {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${security.jwt.token.secret-access-key}")
    private String secretAccessKey;

    @Value("${security.jwt.token.secret-refresh-key}")
    private String secretRefreshKey;

    @Value("${security.jwt.token.access-token-expire-validity}")
    private Long accessTokenValidity;

    @Value("${security.jwt.token.refresh-token-expire-validity}")
    private Long refreshTokenValidity;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtUtils(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostConstruct
    protected void init() {
        secretAccessKey = Base64.getEncoder().encodeToString(secretAccessKey.getBytes());
        secretRefreshKey = Base64.getEncoder().encodeToString(secretRefreshKey.getBytes());
    }

    public String generateAccessToken(User user, String hash) {
        Date validity = new Date(new Date().getTime() + accessTokenValidity);
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("id", user.getId())
                .claim("roles", user.getRoles())
                .claim("hash", hash)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretAccessKey)
                .compact();
    }

    public String generateRefreshToken(String login, String hash) {
        Date validity = new Date(new Date().getTime() + refreshTokenValidity);
        return Jwts.builder()
                .setSubject(login)
                .claim("hash", hash)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretRefreshKey)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            String userName = getUsernameFromAccessToken(token);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } catch (UsernameNotFoundException exception) {
            throw new JwtCustomException(exception.getMessage());
        }
    }

    private String getUsernameFromAccessToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretAccessKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getHashFromRefreshToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretRefreshKey)
                .parseClaimsJws(token)
                .getBody()
                .get("hash", String.class);
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

    public void validateRefreshToken(String token) {
        try {
            validateToken(token, secretRefreshKey);
        } catch (ExpiredJwtException exception) {
            throw new JwtRefreshExpiredException(exception.getMessage());
        }
    }

    private void validateToken(String token, String secretKey) {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
    }
}
