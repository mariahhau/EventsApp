package com.mariahhau.events.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mariahhau.events.Service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;
    
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception{    
        //TODO: fix deprecated methods
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
        .cors().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .formLogin().disable()
        .securityMatcher("/**")
        .authorizeHttpRequests(registry -> registry
        .requestMatchers("api/events").permitAll() //Makes this path accessible for everyone
        .requestMatchers("api/events/{id}").permitAll()
        .requestMatchers("api/events/{id}/register").permitAll()
        .requestMatchers("api/login").permitAll() 
        .requestMatchers("api/signUp").permitAll() 
        .anyRequest().authenticated()); //User needs to be authenticated for any request that is not explicitly allowed

        return http.build();
        
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("WebSecurityConfig: passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        System.out.println("WebSecurityConfig: authenticationManager");
        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(customUserDetailService)
            .passwordEncoder(passwordEncoder())
            .and().build();
    }
    
}
