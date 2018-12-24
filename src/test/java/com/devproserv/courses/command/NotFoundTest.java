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

import javax.servlet.http.HttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Contains unit-tests to check functionality of {@link NotFound} class.
 *
 * @since 1.0.0
 */
public class NotFoundTest {

    /**
     * HTTP request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Command Not found.
     */
    private Command nfound;

    /**
     * Prepare data.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.nfound = new NotFound();
    }

    @Test
    public void testExecuteCommandOk() {
        final String path = this.nfound.path(this.request);
        Assert.assertEquals(
            String.format("Should be equal to %s", NotFound.NOT_FOUND_PAGE),
            NotFound.NOT_FOUND_PAGE, path
        );
    }
}
