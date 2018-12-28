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
 * Contains unit-tests to check functionality of {@link SignUpValidation} class.
 *  The parameters "login" and "password" are checked.
 *
 * @since 1.0.0
 */
final class SignUpLoginValidationTest {
    /**
     * Message to start with letter.
     */
    private static final String MSG_START =
        "Username shouldn't start with digit or non letter!";

    /**
     * Test.
     */
    @Test
    void testValidatedOkLoginEndsDigit() {
        final Validation validation = new SignUpValidation(
            "user4", "ewrgas", "John", "Doe", "Mathematics"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertTrue(result.valid()),
            () -> Assertions.assertFalse(result.reason().isPresent())
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedNull() {
        final Validation validation = new SignUpValidation(
            null, null, "Peter", "Jackson", "Physics"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be null!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedEmpty() {
        final Validation validation = new SignUpValidation(
            "", "5fyfs", "Arnold", "Schwarzenegger", "Bodybuilding"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be empty!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginBeginsDigit() {
        final Validation validation = new SignUpValidation(
            "25user", "hfd4te", "Stanley ", "Kubrick", "Cinematography"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                SignUpLoginValidationTest.MSG_START,
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginBeginsNotLetter() {
        final Validation validation = new SignUpValidation(
            "#user", "knm6s%", "Bruce", "Willis", "Die hard"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                SignUpLoginValidationTest.MSG_START,
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginContainsSpaceOne() {
        final Validation validation = new SignUpValidation(
            " user", "bhg*dg", "Brad", "Pitt", "Button"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                SignUpLoginValidationTest.MSG_START,
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginContainsSpaceTwo() {
        final Validation validation = new SignUpValidation(
            "user name", "kbetrs", "Anne", "Hathaway", "Interstellar"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Username should contain only letters and digits!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLoginStartsWithUnderscore() {
        final Validation validation = new SignUpValidation(
            "_user", "kb$#rd", "Leonardo", "Dicaprio", "Inception"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                SignUpLoginValidationTest.MSG_START,
                result.reason().orElse("")
            )
        );
    }
}
