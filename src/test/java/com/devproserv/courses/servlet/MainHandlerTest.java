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

package com.devproserv.courses.servlet;

import com.devproserv.courses.form.EnrollForm;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Contains unit-tests to check functionality of {@link MainHandler} class.
 *
 * @since 1.0.0
 */
class MainHandlerTest {
    /**
     * Main handler.
     */
    @InjectMocks
    private MainHandler handler;

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
        this.handler = new MainHandler();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test.
     * @throws IOException IO exception
     * @throws ServletException Servlet exception
     */
    @Test
    void testDoPostOk() throws IOException, ServletException {
        Mockito.when(this.context.getPath(this.request))
            .thenReturn(EnrollForm.STUDENT_PAGE);
        Mockito.when(this.request.getRequestDispatcher(EnrollForm.STUDENT_PAGE))
            .thenReturn(this.dispatcher);
        this.handler.doPost(this.request, this.response);
        Mockito.verify(this.dispatcher, Mockito.atLeastOnce())
            .forward(this.request, this.response);
    }
}
