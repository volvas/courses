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

import com.devproserv.courses.form.EnrollForm;
import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter forbids direct access to internal JSP. In case of direct typing
 * address in the browser the filter redirects to home page.
 *
 * @since 0.5.0
 */
@WebFilter(urlPatterns = {EnrollForm.STUDENT_PAGE, JspFilter.LECTURER_PAGE},
    dispatcherTypes = {DispatcherType.REQUEST})
public final class JspFilter implements Filter {
    /**
     * Home page file name.
     */
    public static final String HOME_PAGE = "/index.html";

    /**
     * Lecturer JSP page file name.
     */
    static final String LECTURER_PAGE = "/students.jsp";

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JspFilter.class);

    @Override
    public void init(final FilterConfig config) {
        LOGGER.debug("The instance initialized.");
    }

    @Override
    public void doFilter(
        final ServletRequest request, final ServletResponse response, final FilterChain chain
    ) throws IOException {
        final HttpServletRequest hrequest = (HttpServletRequest) request;
        final HttpServletResponse hresponse = (HttpServletResponse) response;
        hresponse.sendRedirect(hrequest.getContextPath() + JspFilter.HOME_PAGE);
    }

    @Override
    public void destroy() {
        LOGGER.debug("The instance destroyed.");
    }
}
