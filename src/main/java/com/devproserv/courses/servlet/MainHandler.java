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

import com.devproserv.courses.command.Commands;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles all web forms in the application like registration
 * and login procedures, handling course and student lists.
 *
 * @since 0.5.0
 */
@WebServlet(urlPatterns = {"/login", "/courses"}, name = "mainHandler")
public final class MainHandler extends HttpServlet {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = -1021229685792407270L;

    /**
     * Command manager.
     */
    private Commands commands;

    @Override
    public void init(final ServletConfig config) {
        this.commands = new Commands().build();
    }

    @Override
    protected void doPost(
        final HttpServletRequest request, final HttpServletResponse response
    ) throws ServletException, IOException {
        final String path = this.commands.path(request);
        final RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        final String encoding = "UTF-8";
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        dispatcher.forward(request, response);
    }
}
