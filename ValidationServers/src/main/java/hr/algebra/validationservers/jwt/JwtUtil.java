
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

    private Key getAccessKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes());
    }

    private Key getRefreshKey() {
        return Keys.hmacShaKeyFor(refreshSecret.getBytes());
    }

    public String generateAccessToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpirationMs))
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpirationMs))
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, getAccessKey());
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, getRefreshKey());
    }

    private boolean validateToken(String token, Key key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // log if you want: e.g. logger.warn("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    public String extractUsernameFromAccessToken(String token) {
        return extractUsername(token, getAccessKey());
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractUsername(token, getRefreshKey());
    }

    private String extractUsername(String token, Key key) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
