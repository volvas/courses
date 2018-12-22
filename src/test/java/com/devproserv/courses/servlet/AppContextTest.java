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

import com.devproserv.courses.config.Conf;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Contains unit-tests to check functionality of {@link AppContext} class.
 *
 * @since 1.0.0
 */
public class AppContextTest {
    /**
     * HTTP request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Application context.
     */
    private AppContext context;

    /**
     * Setup.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.context = new AppContext();
    }

    @Test
    public void testGetPathOk() {
        when(this.request.getParameter("command"))
            .thenReturn(Conf.COMMAND_LOGIN);
        final String path = this.context.getPath(this.request);
        assertEquals("Not login page.", Conf.LOGIN_PAGE, path);
    }

    @Test
    public void testGetPathWrongCommand() {
        when(this.request.getParameter("command"))
            .thenReturn("invalid command");
        final String path = this.context.getPath(this.request);
        assertEquals("Not notfound page", Conf.NOT_FOUND_PAGE, path);
    }
}
