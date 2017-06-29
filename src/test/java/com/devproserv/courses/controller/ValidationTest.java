package com.devproserv.courses.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.devproserv.courses.util.Validation;

/**
 * Test suite for Validation class
 * 
 * @author vovas11
 *
 */
public class ValidationTest {
    
    @Test
    public void testCheckOk() {
        
        String result = Validation.checkCredentials("user", "password");
        assertEquals("ok", result);
    }

    @Test
    public void testCheckCredentialsNull() {
        
        String result = Validation.checkCredentials(null, null);
        assertEquals("Username and password should not be empty!", result);
    }
    
    @Test
    public void testCheckCredentialsEmpty() {
        
        String result = Validation.checkCredentials("", "pass");
        assertEquals("Username and password should not be empty!", result);
    }
    
    @Test
    public void testCheckLoginBeginsDigit() {
        
        String result = Validation.checkCredentials("25user", "pass");
        assertEquals("Username should not start with a digit or non letter!", result);
    }
    
    @Test
    public void testCheckLoginBeginsNotLetter() {
        
        String result = Validation.checkCredentials("#user", "pass");
        assertEquals("Username should not start with a digit or non letter!", result);
    }
    
    @Test
    public void testCheckLoginContainsSpace() {
        
        String result = Validation.checkCredentials(" user", "pass");
        assertEquals("Username should not start with a digit or non letter!", result);
        String result2 = Validation.checkCredentials("user name", "pass");
        assertEquals("Username should contain only letters and digits!", result2);
    }
    
    @Test
    public void testCheckLoginEndsDigit() {
        
        String result = Validation.checkCredentials("user4", "pass");
        assertEquals("ok", result);
    }

}
