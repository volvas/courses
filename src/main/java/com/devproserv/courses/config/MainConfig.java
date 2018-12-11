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
public final class MainConfig {
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
     * Select user SQL query.
     */
    public static final String SEL_USER =
        "SELECT * FROM users WHERE login=? AND password=?";

    /**
     * Select login SQL query.
     */
    public static final String SEL_LOGIN =
        "SELECT * FROM users WHERE login=?";

    /**
     * Insert login SQL query.
     */
    public static final String INS_USER = "INSERT INTO users (firstname,"
        + " lastname, login, password, role) VALUES(?, ?, ?, ?, ?)";

    /**
     * Select student SQL query.
     */
    public static final String INS_STUDENT =
        "INSERT INTO students (stud_id, faculty) VALUES(?, ?)";

    /**
     * Select user fields SQL query.
     */
    public static final String GET_USER_FLDS = "SELECT user_id,"
        + " firstname, lastname, faculty FROM users JOIN students"
        + " ON users.user_id = students.stud_id WHERE login = ?";

    /**
     * Select enrolled courses SQL query.
     */
    public static final String SEL_ENROLL_CRSES = "SELECT * "
        + "FROM courses WHERE courses.course_id IN ("
        + "SELECT student_courses.course_id FROM student_courses, users "
        + "WHERE student_courses.stud_id = users.user_id AND users.login = ?)";

    /**
     * Select available courses SQL query.
     */
    public static final String SEL_AVAIL_CRSES = "SELECT * "
        + "FROM courses WHERE course_id NOT IN ("
        + "SELECT course_id FROM student_courses WHERE stud_id IN ("
        + "SELECT user_id FROM users WHERE login = ?))";

    /**
     * Insert user courses SQL query.
     */
    public static final String INS_USER_CRSES = "INSERT INTO "
        + "student_courses (course_id, stud_id, status) "
        + "VALUES(?, (SELECT users.user_id FROM users WHERE login = ?), "
        + "'STARTED')";

    /**
     * Delete courses from user SQL query.
     */
    public static final String DEL_USER_CRSES = "DELETE FROM "
        + "student_courses WHERE course_id = ? AND stud_id = ?";

    /**
     * Private constructor avoiding instantiation.
     */
    private MainConfig() {
    }
}
