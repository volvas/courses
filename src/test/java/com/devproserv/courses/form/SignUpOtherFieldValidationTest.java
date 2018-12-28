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

import com.devproserv.courses.validation.VldResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Contains unit-tests to check functionality of {@link SignUpValidation} class.
 * The parameters "first name" and "last name" and "faculty" are checked.
 *
 * @since 1.0.0
 */
final class SignUpOtherFieldValidationTest {
    /**
     * Test.
     */
    @Test
    void testValidatedFirstNameNull() {
        final Validation validation = new SignUpValidation(
            "rusty", "vlkdn*gd", null, "Rodgers", "Design"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "First name should not be null!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedFirstNameEmpty() {
        final Validation validation = new SignUpValidation(
            "bruise", "oibm%d", "", "Friedman", "Computer Science"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "First name should not be empty!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLastNameNull() {
        final Validation validation = new SignUpValidation(
            "dubbed", "wbcb$d", "Jadyn", null, "Electronic Systems"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Last name should not be null!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedLastNameEmpty() {
        final Validation validation = new SignUpValidation(
            "sandworm", "lamv%d", "Kenzie", "", "Engineering"
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Last name should not be empty!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedFacultyNull() {
        final Validation validation = new SignUpValidation(
            "slinky", "klvln&d", "Sebastian", "Beasley", null
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Faculty should not be null!",
                result.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void testValidatedFacultyEmpty() {
        final Validation validation = new SignUpValidation(
            "petal", "nvmx^kdf", "Marlene", "Lam", ""
        );
        final VldResult result = validation.validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(result.valid()),
            () -> Assertions.assertEquals(
                "Faculty should not be empty!",
                result.reason().orElse("")
            )
        );
    }
}
