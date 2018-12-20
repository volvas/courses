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

package com.devproserv.courses.form;

import com.devproserv.courses.config.MainConfig;
import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Handles enroll form.
 */
public final class EnrollForm implements Form {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(EnrollForm.class.getName());

    /**
     * Application context.
     */
    private final AppContext context;

    /**
     * Course handler
     */
    private final CourseHandling courseHandling;

    /**
     * User.
     */
    private Student user;

    /**
     * Constructor.
     * @param context Application context
     * @param courseHandling Course handler
     */
    public EnrollForm(
        final AppContext context, final CourseHandling courseHandling
    ) {
        this.context = context;
        this.courseHandling = courseHandling;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return MainConfig.LOGIN_PAGE;
        }

        user = (Student) session.getAttribute(session.getId());
        if (user == null) {
            return MainConfig.LOGIN_PAGE;
        }

        final String courseIdStr = request.getParameter(
            courseHandling.courseIdParameter()
        );
        final Validation validation = new NumberValidation(courseIdStr);
        return validation.validated()
                ? validPath(request)
                : invalidPath(validation, request);
    }

    private String invalidPath(final Validation validation,
                               final HttpServletRequest request) {
        LOGGER.info("Invalid credentials for login {}", user.getLogin());
        request.setAttribute(
            courseHandling.errorMessageParameter(), validation.errorMessage()
        );
        user.prepareJspData(request);
        return MainConfig.STUDENT_PAGE;
    }

    private String validPath(final HttpServletRequest request) {
        final String courseIdStr = request.getParameter(courseHandling.courseIdParameter());
        final int courseId = Integer.parseInt(courseIdStr);
        final Course course = new Course(context);
        course.setId(courseId);
        return courseHandling.path(course, user, request);
    }
}
