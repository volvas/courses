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

package com.devproserv.courses.command;

import com.devproserv.courses.form.SignUpForm;
import com.devproserv.courses.servlet.AppContext;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Contains unit-tests to check functionality of {@link SignUp} class.
 *
 * @since 1.0.0
 */
class SignUpTest {
    /**
     * Application context.
     */
    @Mock
    private AppContext context;

    /**
     * HTTP request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Sign Up command.
     */
    private SignUp signup;

    /**
     * Prepare data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.signup = new SignUp(this.context);
    }

    /**
     * Checks if path is correct.
     */
    @Test
    void testPathOk() {
        final String path = this.signup.path(this.request);
        Assertions.assertEquals(SignUpForm.SIGNUP_PAGE, path);
    }
}
