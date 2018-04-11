package com.devproserv.courses.form;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit-tests to check functionality of {@link NumberValidation} class
 *
 */
public class NumberValidationTest {

    @Test
    public void testValidatedOk() {
        Validation validation = new NumberValidation("32");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertTrue(result);
        assertEquals("ok", message);
    }

    @Test
    public void testValidatedNull() {
        Validation validation = new NumberValidation(null);
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Field should not be empty!", message);
    }

    @Test
    public void testValidatedEmpty() {
        Validation validation = new NumberValidation("");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Field should not be empty!", message);
    }

    @Test
    public void testValidatedNotNumber() {
        Validation validation = new NumberValidation("3dh2");
        boolean result = validation.validated();
        String message = validation.errorMessage();
        assertFalse(result);
        assertEquals("Field should contain only digits", message);
    }
}
