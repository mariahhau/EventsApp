package com.mariahhau.events.Security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
        .userId(Long.valueOf(jwt.getSubject()))
        .username(jwt.getClaim("u").asString())
        .email(jwt.getClaim("e").asString()) 
        .authorities(extractAuthoritiesFromClaim(jwt))
        .build();
    }
    


    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("r");
        if (claim.isNull() || claim.isMissing()) return List.of();

        return claim.asList(SimpleGrantedAuthority.class);
    }
}
