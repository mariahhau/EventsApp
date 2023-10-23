package com.mariahhau.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.mariahhau.events.Utilities;

public class UtilityTest {

    @Test
    void invalidUsernameReturnsFalse() {
        assertFalse(Utilities.validateUsername("")); //too short username
        assertFalse(Utilities.validateUsername("Aaaaaaaaaaaaaaaaaaaaa")); //too long username
        assertFalse(Utilities.validateUsername("asd.")); //contains an invalid character
        assertFalse(Utilities.validateUsername("asd!")); //contains an invalid character

    }

    @Test
    void validUsernameReturnsTrue() {
        assertTrue(Utilities.validateUsername("A")); //minimum length 1 character
        assertTrue(Utilities.validateUsername("Aaaaaaaaaaaaaaaaaaaa")); //maximum length 20 characters
        assertTrue(Utilities.validateUsername("Asd1")); //Numbers are allowed
        assertTrue(Utilities.validateUsername("Asd_asd")); //Underscore is allowed

    }

    @Test
    void invalidEmailReturnsFalse() {
        assertFalse(Utilities.validateEmail("test.fi"));
        assertFalse(Utilities.validateEmail("test@fi"));
        assertFalse(Utilities.validateEmail("test@test."));
        assertFalse(Utilities.validateEmail("test@test..fi")); 
        assertFalse(Utilities.validateEmail("@test.fi")); 
        assertFalse(Utilities.validateEmail("test@.com")); 

    }

    @Test
    void validEmailReturnsTrue() {
        assertTrue(Utilities.validateEmail("test@test.fi")); 
        assertTrue(Utilities.validateEmail("test_test@test.com")); 
        assertTrue(Utilities.validateEmail("test123@test.net")); 
        assertTrue(Utilities.validateEmail("123@test.net")); 

    }
    
}
