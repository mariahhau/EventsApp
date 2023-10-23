package com.mariahhau.events.Security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    public UserPrincipalAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        System.out.println("UserPrincipalAuthenticationToken: getCredentials ");
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        System.out.println("UserPrincipalAuthenticationToken: getPrincipal ");
        return principal;
    }

    
}
