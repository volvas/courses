package com.devproserv.courses.form;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit-tests to check functionality of {@link SignUpValidation} class
 *
 */
public class SignUpValidationTest {

    @Test
    public void testValidatedOk() {
        Validation validation = new SignUpValidation("user", "password", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertTrue(result);
        assertEquals("ok", message);
    }

    @Test
    public void testValidatedOkLoginEndsDigit() {
        Validation validation = new SignUpValidation("user4", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertTrue(result);
        assertEquals("ok", message);
    }

    @Test
    public void testValidatedNull() {
        Validation validation = new SignUpValidation(null, null, "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username and password should not be empty!", message);
    }

    @Test
    public void testValidatedEmpty() {
        Validation validation = new SignUpValidation("", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username and password should not be empty!", message);
    }

    @Test
    public void testValidatedLoginBeginsDigit() {
        Validation validation = new SignUpValidation("25user", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedLoginBeginsNotLetter() {
        Validation validation = new SignUpValidation("#user", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedLoginContainsSpace1() {
        Validation validation = new SignUpValidation(" user", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedLoginContainsSpace2() {
        Validation validation = new SignUpValidation("user name", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should contain only letters and digits!", message);
    }

    @Test
    public void testValidatedLoginStartsWithUnderscore() {
        Validation validation = new SignUpValidation("_user", "pass", "firstName", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Username should not start with a digit or non letter!", message);
    }

    @Test
    public void testValidatedFirstNameNull() {
        Validation validation = new SignUpValidation("user", "pass", null, "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("First name should not be empty!", message);
    }

    @Test
    public void testValidatedFirstNameEmpty() {
        Validation validation = new SignUpValidation("user", "pass", "", "lastName", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("First name should not be empty!", message);
    }

    @Test
    public void testValidatedLastNameNull() {
        Validation validation = new SignUpValidation("user", "pass", "firstName", null, "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Last name should not be empty!", message);
    }

    @Test
    public void testValidatedLastNameEmpty() {
        Validation validation = new SignUpValidation("user", "pass", "firstName", "", "faculty");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Last name should not be empty!", message);
    }

    @Test
    public void testValidatedFacultyNull() {
        Validation validation = new SignUpValidation("user", "pass", "firstName", "lastName", null);
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Faculty should not be empty!", message);
    }

    @Test
    public void testValidatedFacultyEmpty() {
        Validation validation = new SignUpValidation("user", "pass", "firstName", "lastName", "");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Faculty should not be empty!", message);
    }
}
