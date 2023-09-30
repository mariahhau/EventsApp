package com.mariahhau.events.Security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties properties;


    //TODO tee tästä parempi
    public DecodedJWT decode(String token) {
        System.out.println("JwtDecoder: " + token);
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
        .build()
        .verify(token);
    }
}
