package com.devproserv.courses.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.devproserv.courses.form.Validation;

/**
 * Test suite for Validation class
 * 
 * @author vovas11
 *
 */
public class ValidationTest {

    // tests for method checkCredentials with two arguments
    @Test
    public void testCheckCredentialsOk() {
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
    public void testCheckCredentialsLoginBeginsDigit() {
        String result = Validation.checkCredentials("25user", "pass");
        assertEquals("Username should not start with a digit or non letter!", result);
    }

    @Test
    public void testCheckCredentialsLoginBeginsNotLetter() {
        String result = Validation.checkCredentials("#user", "pass");
        assertEquals("Username should not start with a digit or non letter!", result);
    }

    @Test
    public void testCheckCredentialsLoginContainsSpace() {
        String result = Validation.checkCredentials(" user", "pass");
        assertEquals("Username should not start with a digit or non letter!", result);
        String result2 = Validation.checkCredentials("user name", "pass");
        assertEquals("Username should contain only letters and digits!", result2);
    }

    @Test
    public void testCheckCredentialsLoginEndsDigit() {
        String result = Validation.checkCredentials("user4", "pass");
        assertEquals("ok", result);
    }

    // tests for the overloaded method checkCredentials with 5 arguments
    @Test
    public void testCheckCredentialsFirstNameOk() {
        String result = Validation.checkCredentials("user", "password", "firstName", "lastName", "faculty");
        assertEquals("ok", result);
    }

    @Test
    public void testCheckCredentiaFirstNamelsNull() {
        String result = Validation.checkCredentials("user", "password", null, "lastName", "faculty");
        assertEquals("First name should not be empty!", result);
    }

    @Test
    public void testCheckCredentialsFirstNameEmpty() {
        String result = Validation.checkCredentials("user", "password", "", "lastName", "faculty");
        assertEquals("First name should not be empty!", result);
    }
    
    @Test
    public void testCheckCredentiaLastNamelsNull() {
        String result = Validation.checkCredentials("user", "password", "firstName", null, "faculty");
        assertEquals("Last name should not be empty!", result);
    }

    @Test
    public void testCheckCredentialsLastNameEmpty() {
        String result = Validation.checkCredentials("user", "password", "firstName", "", "faculty");
        assertEquals("Last name should not be empty!", result);
    }
    
    @Test
    public void testCheckCredentiaFacultylsNull() {
        String result = Validation.checkCredentials("user", "password", "firstName", "lastName", null);
        assertEquals("Faculty should not be empty!", result);
    }

    @Test
    public void testCheckCredentialsFacultyEmpty() {
        String result = Validation.checkCredentials("user", "password", "firstName", "lastName", "");
        assertEquals("Faculty should not be empty!", result);
    }

    @Test
    public void testCheckIntegerOk() {
        String result = Validation.checkInteger("32");
        assertEquals("ok", result);
    }

    @Test
    public void testCheckIntegerNotNumber() {
        String result = Validation.checkInteger("3dh2");
        assertEquals("Invalid course ID!", result);
    }

    @Test
    public void testCheckIntegerEmpty() {
        String result = Validation.checkInteger("");
        assertEquals("Invalid course ID!", result);
    }
}
