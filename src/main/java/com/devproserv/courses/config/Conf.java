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

package com.devproserv.courses.config;

/**
 * Service class stores configuration of the application.
 *
 * @since 1.0.0
 */
public final class Conf {
    /**
     * Home page file name.
     */
    public static final String HOME_PAGE = "/index.html";

    /**
     * Not found page file name.
     */
    public static final String NOT_FOUND_PAGE = "/404.html";

    /**
     * Generic error page file name.
     */
    public static final String GENERIC_ERR_PAGE = "/errorgen.html";

    /**
     * Exception page file name.
     */
    public static final String EXCEPTION_PAGE = "/errorexcep.html";

    /**
     * Sign up JSP page file name.
     */
    public static final String SIGNUP_PAGE = "/signup.jsp";

    /**
     * Login JSP page file name.
     */
    public static final String LOGIN_PAGE = "/login.jsp";

    /**
     * Course JSP page file name.
     */
    public static final String STUDENT_PAGE = "/courses.jsp";

    /**
     * Lecturer JSP page file name.
     */
    public static final String LECTURER_PAGE = "/students.jsp";

    /**
     * Sign up command name.
     */
    public static final String COMMAND_SIGNUP = "signup";

    /**
     * Login command name.
     */
    public static final String COMMAND_LOGIN = "login";

    /**
     * Logout command name.
     */
    public static final String COMMAND_LOGOUT = "logout";

    /**
     * Enroll command name.
     */
    public static final String COMMAND_SUBSCRIBE = "subscribe";

    /**
     * Unroll command name.
     */
    public static final String COMMAND_UNROLL = "unsubscribe";

    /**
     * Private constructor avoiding instantiation.
     */
    private Conf() {
    }
}
