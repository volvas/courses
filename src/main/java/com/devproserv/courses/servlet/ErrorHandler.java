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

import com.devproserv.courses.command.NotFound;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet handles errors related to wrong requests and JVM's exceptions.
 * 
 * @since 1.0.0
 */
@WebServlet(urlPatterns = {"/error"}, name = "errorHandler")
public final class ErrorHandler extends HttpServlet {
    /**
     * Exception page file name.
     */
    static final String EXCEPTION_PAGE = "/errorexcep.html";
    /**
     * Generic error page file name.
     */
    static final String GENERIC_ERR_PAGE = "/errorgen.html";

    /**
     * Serial number.
     */
    private static final long serialVersionUID = -9152573062248666207L;

    @Override
    protected void doGet(final HttpServletRequest request,
        final HttpServletResponse response
    ) throws ServletException, IOException {
        final Throwable throwable = (Throwable) request.getAttribute(
            "javax.servlet.error.exception"
        );
        final Integer statusCode = (Integer) request.getAttribute(
            "javax.servlet.error.status_code"
        );
        String pageToRedirect = GENERIC_ERR_PAGE;
        if (throwable != null) {
            pageToRedirect = EXCEPTION_PAGE;
        } else if (statusCode == 404) {
            pageToRedirect = NotFound.NOT_FOUND_PAGE;
        }
        final RequestDispatcher dispatcher = request.getRequestDispatcher(
            pageToRedirect
        );
        dispatcher.forward(request, response);
    }
}
