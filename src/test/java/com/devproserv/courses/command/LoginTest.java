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

package com.devproserv.courses.command;

import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.UserRoles;
import com.devproserv.courses.validation.results.VldResult;
import com.devproserv.courses.validation.results.VldResultAggr;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
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
     * Validation aggregator.
     */
    private VldResultAggr aggr;

    /**
     * Result of validation.
     */
    @Mock
    private VldResult vldres;

    /**
     * User manager.
     */
    @Mock
    private UserRoles users;

    /**
     * Prepare data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.aggr = Mockito.mock(VldResultAggr.class, Answers.RETURNS_SELF);
        Mockito.when(this.aggr.checkUsername(Mockito.anyString())).thenReturn(this.aggr);
        Mockito.when(this.aggr.checkPassword(Mockito.anyString())).thenReturn(this.aggr);
        Mockito.when(this.aggr.aggregate()).thenReturn(this.vldres);
    }

    /**
     * Checks if login goes on invalid branch.
     */
    @Test
    void responseWithInitPageWhenInvalidBranch() {
        final Login login = new Login(this.aggr, this.users);
        final Response response = login.response(this.request);
        Assertions.assertEquals(EnrollForm.LOGIN_PAGE, response.getPath());
    }

    /**
     * Checks if login goes on valid branch.
     */
    @Test
    void responseWithFurtherPageWhenValidBranch() {
        final String path = "authenticated";
        Mockito.when(this.vldres.valid()).thenReturn(true);
        this.users = Mockito.mock(UserRoles.class, Answers.RETURNS_SELF);
        Mockito.when(this.users.build(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(this.users);
        Mockito.when(this.users.response(this.request)).thenReturn(
            new Response(path, Collections.emptyMap())
        );
        final Login login = new Login(this.aggr, this.users);
        final Response response = login.response(this.request);
        Assertions.assertEquals(path, response.getPath());
    }
}
