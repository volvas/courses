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

import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

public class EnrollForm implements Form {

    private static final Logger LOGGER = LogManager.getLogger(EnrollForm.class.getName());

    private final AppContext appContext;
    private final CourseHandling courseHandling;

    private Student user;


    public EnrollForm(AppContext appContext, CourseHandling courseHandling) {
        this.appContext = appContext;
        this.courseHandling = courseHandling;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        // check if session still exists
        HttpSession session = request.getSession(false);
        if (session == null) {
            return LOGIN_PAGE;
        }

        user = (Student) session.getAttribute(session.getId());
        if (user == null) {
            return LOGIN_PAGE;
        }

        /* checks the selection for the valid input */
        final String courseIdStr = request.getParameter(courseHandling.courseIdParameter());
        Validation validation = new NumberValidation(courseIdStr);
        return validation.validated() ? validPath(request) : invalidPath(validation, request);
    }

    private String invalidPath(final Validation validation,
                               final HttpServletRequest request) {
        LOGGER.info("Invalid credentials for login " + user.getLogin());
        request.setAttribute(courseHandling.errorMessageParameter(),
                validation.errorMessage());
        user.prepareJspData(request);
        return STUDENT_PAGE;
    }

    private String validPath(final HttpServletRequest request) {
        final String courseIdStr = request.getParameter(courseHandling.courseIdParameter());
        int courseId = Integer.parseInt(courseIdStr);

        Course course = new Course(appContext);
        course.setId(courseId);
        return courseHandling.path(course, user, request);
    }
}
