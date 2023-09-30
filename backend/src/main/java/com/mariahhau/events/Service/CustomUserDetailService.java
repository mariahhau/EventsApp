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

    //Huom. UserPrincipal implements UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailService; loadUserByUsername  (kutsuu userserviceimpl.findUserByUsername)");

        var user = userService.findUserByUsername(username).orElseThrow(); //TODO: throw mikä exception

        System.out.println("loadUserByUsername: var user arvot " + user.toString());

        System.out.print ("CustomUserDetailService: return UserPrincipal.builder ");
      /*System.out.println( UserPrincipal.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
            .password(user.getPassword())
            .build() + "\n\n");
*/


        return UserPrincipal.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
            .password(user.getPassword())
            .build();
    }

    
}
