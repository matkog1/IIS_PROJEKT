package hr.algebra.validationservers.controller;

import hr.algebra.validationservers.jwt.JwtUtil;
import hr.algebra.validationservers.model.User;
import hr.algebra.validationservers.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Username already exists");
        }
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(List.of("ROLE_USER"))
                .build();
        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return userRepo.findByUsername(req.getUsername())
                .filter(u -> passwordEncoder.matches(req.getPassword(), u.getPassword()))
                .map(u -> {
                    String access  = jwtUtil.generateAccessToken(u.getUsername());
                    String refresh = jwtUtil.generateRefreshToken(u.getUsername());
                    return ResponseEntity.ok(new AuthResponse(access, refresh));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest r) {
        if (!jwtUtil.validateRefreshToken(r.getRefreshToken())) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
        String username = jwtUtil.extractUsernameFromRefreshToken(r.getRefreshToken());
        String newAccess = jwtUtil.generateAccessToken(username);
        return ResponseEntity.ok(new AuthResponse(newAccess, r.getRefreshToken()));
    }

    @Data static class AuthRequest {
        private String username, password;
    }
    @Data static class RefreshRequest {
        private String refreshToken;
    }
    @Data static class AuthResponse {
        private final String accessToken, refreshToken;
    }
}
