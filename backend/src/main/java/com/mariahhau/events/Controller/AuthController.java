package com.mariahhau.events.Controller;

import com.mariahhau.events.Security.JwtIssuer;
import com.mariahhau.events.Security.UserPrincipal;
import com.mariahhau.events.Service.UserServiceImpl;
import com.mariahhau.events.Utilities;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mariahhau.events.Database.Documents.User;
import com.mariahhau.events.Model.LoginRequest;
import com.mariahhau.events.Model.LoginResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final UserServiceImpl userService;

  
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        System.out.println("\nAuthController: login\n");

        System.out.println("request.username: " + request.getUsername() + " pw:" + request.getPassword());
        var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        System.out.println("auth: " + auth);
        SecurityContextHolder.getContext().setAuthentication(auth); //TODO ?

        var principal = (UserPrincipal) auth.getPrincipal();
        System.out.println("AuthController, principal " + principal + " id" + principal.getUserId());

        
        var roles = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), principal.getEmail(), roles);
        System.out.println("token:" +token);

        return LoginResponse.builder()
        .accessToken(token)
        .build();

    }

    //Sign up 
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> payload) {
        User user = new User();
      
        if (payload.get("username") != null) {
            String username = payload.get("username");
            if (!Utilities.validateUsername(username)) {
                return new ResponseEntity<String>("Username is too short or contains invalid characters", HttpStatus.BAD_REQUEST); 
            }
            user.setUsername(username);
        
        }else return new ResponseEntity<String>("Username cannot be null", HttpStatus.BAD_REQUEST); 
        
        if (payload.get("email") != null) {

            String email = payload.get("email");

            if (!Utilities.validateEmail(email)) {
                return new ResponseEntity<String>("Invalid email address", HttpStatus.BAD_REQUEST); 
            }
            user.setEmail(email);
        } else return new ResponseEntity<String>("Email cannot be null", HttpStatus.BAD_REQUEST); 

        
        if (payload.get("password") != null) {
            user.setPassword(passwordEncoder.encode(payload.get("password")));
        } else return new ResponseEntity<String>("Password cannot be null", HttpStatus.BAD_REQUEST); 
        
        user.setRole("USER");

        if (userService.saveUser(user) == 0) {
            return new ResponseEntity<String>("User created successfully", HttpStatus.CREATED);
        } else return new ResponseEntity<String>("Username is already taken", HttpStatus.BAD_REQUEST);

        
        
    }

    
    
}
