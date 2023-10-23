package com.mariahhau.events.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Configuration
public class JwtProperties {
    /**
     * Secret key used for issuing JWT
     */
    @Value("${secret}")
    private String secretKey;
    
}
