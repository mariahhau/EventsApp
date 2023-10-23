package com.mariahhau.events.Service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mariahhau.events.Security.UserPrincipal;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userService.findUserByUsername(username).orElseThrow(); 

        return UserPrincipal.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
            .password(user.getPassword())
            .build();
    }

    
}
