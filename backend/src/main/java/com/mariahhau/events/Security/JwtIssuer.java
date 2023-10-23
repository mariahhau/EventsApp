package com.mariahhau.events.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;

    public String issue(long userId, String username, String email, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS))) //should be much shorter in production
                .withClaim("u", username)
                .withClaim("e", email)
                .withClaim("r", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
                
    }
}
