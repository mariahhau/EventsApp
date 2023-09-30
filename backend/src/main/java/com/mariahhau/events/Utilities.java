package com.mariahhau.events;

import java.util.regex.Pattern;

public final class Utilities {


    public static boolean validateUsername(String username) {

        String regex = "^[a-zA-Z0-9_]+$";
        if (username.length() < 1 || username.length() > 20) {
            return false;
        }
        System.out.println("Username matches: " +Pattern.compile(regex).matcher(username).matches());

        return Pattern.compile(regex).matcher(username).matches();

                
    }

    public static boolean validateEmail(String email) {

        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        
        System.out.println("Email matches: " +Pattern.compile(regex).matcher(email).matches());

        return Pattern.compile(regex).matcher(email).matches();

                
    }
    
}
