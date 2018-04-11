package com.devproserv.courses.form;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit-tests to check functionality of {@link LoginValidation} class
 *
 */
public class LoginValidationTest {

    @Test
    public void testValidatedOk() {
        Validation validation = new LoginValidation("user", "password");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertTrue(result);
        assertEquals("ok", message);
    }

    @Test
    public void testValidatedOkLoginEndsDigit() {
        Validation validation = new LoginValidation("user4", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertTrue(result);
        assertEquals("ok", message);
    }

    @Test
    public void testValidatedNull() {
        Validation validation = new LoginValidation(null, null);
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username and password should not be empty!", message);
    }

    @Test
    public void testValidatedEmpty() {
        Validation validation = new LoginValidation("", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username and password should not be empty!", message);
    }

    @Test
    public void testValidatedLoginBeginsDigit() {
        Validation validation = new LoginValidation("25user", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedLoginBeginsNotLetter() {
        Validation validation = new LoginValidation("#user", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedLoginContainsSpace1() {
        Validation validation = new LoginValidation(" user", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedLoginContainsSpace2() {
        Validation validation = new LoginValidation("user name", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should contain only letters and digits!", message);
    }

    @Test
    public void testValidatedLoginStartsWithUnderscore() {
        Validation validation = new LoginValidation("_user", "pass");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }
}
