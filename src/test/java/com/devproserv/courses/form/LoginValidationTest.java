/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Vladimir
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.devproserv.courses.form;

import org.junit.Assert;
import org.junit.Test;

/**
 * Contains unit-tests to check functionality of {@link LoginValidation} class.
 *
 * @since 1.0.0
 */
public class LoginValidationTest {
    /**
     * Test.
     */
    @Test
    public void testValidatedOk() {
        final Validation validation = new LoginValidation("user", "password");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertTrue(result);
        Assert.assertEquals("ok", message);
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedOkLoginEndsDigit() {
        final Validation validation = new LoginValidation("user4", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertTrue(result);
        Assert.assertEquals("ok", message);
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedNull() {
        final Validation validation = new LoginValidation(null, null);
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username and password should not be empty!", message
        );
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedEmpty() {
        final Validation validation = new LoginValidation("", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username and password should not be empty!", message
        );
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedLoginBeginsDigit() {
        final Validation validation = new LoginValidation("25user", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username should not start with a digit or non letter!", message
        );
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedLoginBeginsNotLetter() {
        final Validation validation = new LoginValidation("#user", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username should not start with a digit or non letter!", message
        );
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedLoginContainsSpaceOne() {
        final Validation validation = new LoginValidation(" user", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username should not start with a digit or non letter!", message
        );
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedLoginContainsSpaceTwo() {
        final Validation validation = new LoginValidation("user name", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username should contain only letters and digits!", message
        );
    }

    /**
     * Test.
     */
    @Test
    public void testValidatedLoginStartsWithUnderscore() {
        final Validation validation = new LoginValidation("_user", "pass");
        final boolean result = validation.validated();
        final String message = validation.errorMessage();
        Assert.assertFalse(result);
        Assert.assertEquals(
            "Username should not start with a digit or non letter!", message
        );
    }
}
