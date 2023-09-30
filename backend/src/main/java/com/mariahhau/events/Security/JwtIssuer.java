package com.mariahhau.events.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.util.List;


//TODO: productionissa lyhempi expiration aika (refresh token?)
@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;

    public String issue(long userId, String username, String email, List<String> roles) {
        System.out.println("JwtIssuer: issue, userId" + userId);
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("u", username)
                .withClaim("e", email) //TODO email??
                .withClaim("r", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
                

    }
}
