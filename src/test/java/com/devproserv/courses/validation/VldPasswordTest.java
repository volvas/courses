/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Vladimir
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
 * Contains unit-tests to check functionality of {@link VldPassword} class.
 *
 * @since 0.5.0
 */
class VldPasswordTest {
    /**
     * Test.
     */
    @Test
    void okWhenPasswordNormal() {
        final VldResult res = new VldPassword("ewrgas").validate();
        Assertions.assertAll(
            () -> Assertions.assertTrue(res.valid()),
            () -> Assertions.assertFalse(res.reason().isPresent())
        );
    }

    /**
     * Test.
     */
    @Test
    void invalidWhenPasswordNull() {
        final VldResult res = new VldPassword(null).validate();
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
    void invalidWhenPasswordEmpty() {
        final VldResult res = new VldPassword("").validate();
        Assertions.assertAll(
            () -> Assertions.assertFalse(res.valid()),
            () -> Assertions.assertEquals(
                "Username and password should not be empty!", res.reason().orElse("")
            )
        );
    }
}
