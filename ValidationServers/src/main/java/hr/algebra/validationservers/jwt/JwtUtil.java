
package hr.algebra.validationservers.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.access-secret}")
    private String accessSecret;

    @Value("${jwt.refresh-secret}")
    private String refreshSecret;

    @Value("${jwt.access-expiration-ms}")
    private long accessExpirationMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    public String generateAccessToken(String username) {
        return buildToken(username, accessExpirationMs, accessSecret);
    }

    public String generateRefreshToken(String username) {
        return buildToken(username, refreshExpirationMs, refreshSecret);
    }

    public boolean validateAccessToken(String token) {
        return validate(token, accessSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validate(token, refreshSecret);
    }

    public String extractUsernameFromAccessToken(String token) {
        return extractUsername(token, accessSecret);
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractUsername(token, refreshSecret);
    }

    private String buildToken(String subject, long ttlMs, String secret) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ttlMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean validate(String token, String secret) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String extractUsername(String token, String secret) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
