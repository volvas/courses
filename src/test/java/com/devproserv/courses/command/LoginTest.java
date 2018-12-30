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

import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.model.Response;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Contains unit-tests to check functionality of {@link Login} class.
 *
 * @since 0.5.0
 */
class LoginTest {
    /**
     * HTTP request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Login.
     */
    private Login login;

    /**
     * Prepare data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.login = new Login();
    }

    /**
     * Checks correct path.
     */
    @Test
    void testPathOk() {
        final Response response = this.login.response(this.request);
        Assertions.assertEquals(EnrollForm.LOGIN_PAGE, response.getPath(), "Should be null");
    }
}
