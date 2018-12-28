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

import com.devproserv.courses.validation.results.VldResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Contains unit-tests to check functionality of {@link LoginValidation} class.
 *
 * @since 1.0.0
 */
class LoginValidationTest {
    /**
     * Message to start with letter.
     */
    private static final String MSG =
        "Username shouldn't start with digit or non letter!";

    /**
     * Test.
     */
    @Test
    void testValidatedOkLoginEndsDigit() {
        final VldResult res = new LoginValidation("user4", "rtvhcs").validate();
        Assertions.assertAll(
            () -> Assertions.assertTrue(res.valid()),
            () -> Assertions.assertFalse(res.reason().isPresent())
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedNull() {
        final VldResult res = new LoginValidation(null, null).validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be null!",
                res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedEmpty() {
        final VldResult res = new LoginValidation("", "kgfsdhg").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be empty!",
                res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginBeginsDigit() {
        final VldResult res = new LoginValidation("25user", "mgbgt").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                LoginValidationTest.MSG,
                res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginBeginsNotLetter() {
        final VldResult res = new LoginValidation("#user", "nvcko5").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                LoginValidationTest.MSG,
                res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginContainsSpaceOne() {
        final VldResult res = new LoginValidation(" user", "dt4h$g").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                LoginValidationTest.MSG,
                res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginContainsSpaceTwo() {
        final VldResult res = new LoginValidation("user name", "bg").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                "Username should contain only letters and digits!",
                res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginStartsWithUnderscore() {
        final VldResult res = new LoginValidation("_user", "a$6f").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                LoginValidationTest.MSG,
                res.reason().orElse("")
            )
        );
    }
}
