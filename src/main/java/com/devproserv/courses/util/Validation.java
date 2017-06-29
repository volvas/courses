package com.devproserv.courses.util;

/**
 * {@code Validation} class checks if data user inputs are valid
 * 
 * @author vovas11
 */
public class Validation {
    
    
    /**
     * Checks if login and password correspond to valid values (not null, not empty,
     * name should contain only letters and digits and do not start with a digit
     * 
     * @param login
     * @param password
     * @return string containing "ok" if credentials are valid or fault type
     */
    public static String checkCredentials(String login, String password) {
        
        String result = "ok";
        
        if (login == null || password == null) {
            return "Username and password should not be empty!";
        }
        
        if (login.equals("") || password.equals("")) {
            return "Username and password should not be empty!";
        }
        
        if (login.matches("^(\\W|\\d)+.+")) {
            return "Username should not start with a digit or non letter!";
        }
        
        if (login.matches("\\w+\\W+.*")) {
            return "Username should contain only letters and digits!";
        }
        
        
        return result;
    }

}
