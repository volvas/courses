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

package com.devproserv.courses.servlet;

import com.devproserv.courses.command.NotFound;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Contains unit-tests to check functionality of {@link ErrorHandler} class.
 *
 * @since 0.5.0
 */
class ErrorHandlerTest {
    /**
     * Status code.
     */
    private static final String STATUS_CODE = "javax.servlet.error.status_code";

    /**
     * Error handler.
     */
    private ErrorHandler handler;

    /**
     * HTTP request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * HTTP response.
     */
    @Mock
    private HttpServletResponse response;

    /**
     * Request dispatcher.
     */
    @Mock
    private RequestDispatcher dispatcher;

    /**
     * Prepare data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.handler = new ErrorHandler();
    }

    /**
     * Test.
     * @throws Exception Servlet error exception
     */
    @Test
    void testForServerError() throws Exception {
        final Throwable exception = new IllegalStateException();
        Mockito.when(this.request.getAttribute("javax.servlet.error.exception"))
            .thenReturn(exception);
        Mockito.when(this.request.getRequestDispatcher(ErrorHandler.EXCEPTION_PAGE))
            .thenReturn(this.dispatcher);
        this.handler.doGet(this.request, this.response);
        Mockito.verify(this.dispatcher, Mockito.atLeastOnce())
            .forward(this.request, this.response);
    }

    /**
     * Test.
     * @throws Exception Servlet error exception
     */
    @Test
    void testForNotFoundPageError() throws Exception {
        final int notfound = 404;
        Mockito.when(this.request.getAttribute(ErrorHandlerTest.STATUS_CODE))
            .thenReturn(notfound);
        Mockito.when(this.request.getRequestDispatcher(NotFound.NOT_FOUND_PAGE))
            .thenReturn(this.dispatcher);
        this.handler.doGet(this.request, this.response);
        Mockito.verify(this.dispatcher, Mockito.atLeastOnce())
            .forward(this.request, this.response);
    }

    /**
     * Test.
     * @throws Exception Servlet error exception
     */
    @Test
    void testForOtherError() throws Exception {
        final int notallowed = 405;
        Mockito.when(this.request.getAttribute(ErrorHandlerTest.STATUS_CODE))
            .thenReturn(notallowed);
        Mockito.when(this.request.getRequestDispatcher(ErrorHandler.GENERIC_ERR_PAGE))
            .thenReturn(this.dispatcher);
        this.handler.doGet(this.request, this.response);
        Mockito.verify(this.dispatcher, Mockito.atLeastOnce())
            .forward(this.request, this.response);
    }
}
