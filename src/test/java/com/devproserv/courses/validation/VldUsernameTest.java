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

package com.devproserv.courses.validation;

import com.devproserv.courses.validation.results.VldResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Contains unit-tests to check functionality of {@link VldUsername} class.
 *
 * @since 1.0.0
 */
class VldUsernameTest {
    /**
     * Message to start with letter.
     */
    private static final String START = "Username shouldn't start with digit or non letter!";

    /**
     * Message to contain only letters and digits.
     */
    private static final String CONTAIN = "Username should contain only letters and digits!";

    /**
     * Test.
     */
    @Test
    void okWhenUsernameNormal() {
        final VldResult res = new VldUsername("user4").validate();
        Assertions.assertAll(
            () -> Assertions.assertTrue(res.valid()),
            () -> Assertions.assertFalse(res.reason().isPresent())
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameNull() {
        final VldResult res = new VldUsername(null).validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be null!", res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameEmpty() {
        final VldResult res = new VldUsername("").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be empty!", res.reason().orElse("")
            )
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameStartsWithDigit() {
        final VldResult res = new VldUsername("25user").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(VldUsernameTest.START, res.reason().orElse(""))
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameStartsWithSpecialCharacter() {
        final VldResult res = new VldUsername("#user").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(VldUsernameTest.START, res.reason().orElse(""))
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameStartsWithSpace() {
        final VldResult res = new VldUsername(" user").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(VldUsernameTest.START, res.reason().orElse(""))
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameStartsWithUnderscore() {
        final VldResult res = new VldUsername("_user").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(VldUsernameTest.START, res.reason().orElse(""))
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameContainsSpace() {
        final VldResult res = new VldUsername("user name").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(VldUsernameTest.CONTAIN, res.reason().orElse(""))
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenUsernameContainsSpecialCharacter() {
        final VldResult res = new VldUsername("user%name").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(VldUsernameTest.CONTAIN, res.reason().orElse(""))
        );
    }
}
