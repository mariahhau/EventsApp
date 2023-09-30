package com.mariahhau.events.Security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserPrincipal implements UserDetails {

    private final Long userId;
    private final String username;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private final String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("UserPrincipal: getAuthorities");
        return authorities;
    }

    @Override
    public String getPassword() {
        System.out.println("UserPrincipal: getPassword");
        return password;
    }

    @Override
    public String getUsername() {
        System.out.println("UserPrincipal: getUsername " + username);
        return username;
    }

    public String getEmail() {
        System.out.println("UserPrincipal: getEmail " + email);
        return email;
    }

    public Long getUserId() {
        System.out.println("UserPrincipal: getUserId " + userId);
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        System.out.println("UserPrincipal:isAccountNonExpired");
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        System.out.println("UserPrincipal: isAccountNonLocked");
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        System.out.println("UserPrincipal: isCredentialsNonExpired");
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        System.out.println("UserPrincipal: isEnabled?");
        // TODO Auto-generated method stub
        return true;
    }
    
    
}
