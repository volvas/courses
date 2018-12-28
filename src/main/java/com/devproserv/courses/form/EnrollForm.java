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
import com.devproserv.courses.model.Db;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.User;
import com.devproserv.courses.validation.VldNumber;
import com.devproserv.courses.validation.VldResult;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles enroll form.
 *
 * @since 1.0.0
 */
public final class EnrollForm {
    /**
     * Course JSP page file name.
     */
    public static final String STUDENT_PAGE = "/courses.jsp";

    /**
     * Login JSP page file name.
     */
    public static final String LOGIN_PAGE = "/login.jsp";

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
        EnrollForm.class
    );

    /**
     * Application contextDatabase.
     */
    private final Db dbase;

    /**
     * Course handler.
     */
    private final CourseHandling handling;

    /**
     * User.
     */
    private User user;

    /**
     * Constructor.
     *
     * @param handling Course handler
     */
    public EnrollForm(final CourseHandling handling) {
        this(new Db(), handling);
    }

    /**
     * Primary constructor.
     *
     * @param dbase Database
     * @param handling Course handler
     */
    public EnrollForm(final Db dbase, final CourseHandling handling) {
        this.dbase = dbase;
        this.handling = handling;
    }

    /**
     * Checks all data user inputs in web forms.
     *
     * @param request HTTP request
     * @return Response containing a path to the same page in case
     *  of invalidated data or path to further page if validation is successful
     */
    public Response validate(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        final String path;
        if (session == null) {
            path = EnrollForm.LOGIN_PAGE;
        } else {
            this.user = (User) session.getAttribute(session.getId());
            if (this.user == null) {
                path = EnrollForm.LOGIN_PAGE;
            } else {
                final String param = request.getParameter(
                    this.handling.courseIdParameter()
                );
                final VldResult result = new VldNumber(param).validate();
                if (result.valid()) {
                    path = this.validPath(request);
                } else {
                    path = this.invalidPath(result, request);
                }
            }
        }
        return new Response(path, Collections.emptyMap());
    }

    /**
     * Forms an error message in case of invalid path.
     *
     * @param result Validation result
     * @param request HTTP request
     * @return Invalid path
     */
    private String invalidPath(
        final VldResult result, final HttpServletRequest request
    ) {
        LOGGER.info(
            "Invalid credentials for login {}", this.user.getLogin()
        );
        request.setAttribute(
            this.handling.errorMessageParameter(),
            result.reason().orElse("")
        );
        this.user.prepareJspData(request);
        return EnrollForm.STUDENT_PAGE;
    }

    /**
     * Creates a course.
     *
     * @param request HTTP request
     * @return Valid path
     */
    private String validPath(final HttpServletRequest request) {
        final String par = request.getParameter(
            this.handling.courseIdParameter()
        );
        final int id = Integer.parseInt(par);
        final Course course = new Course(this.dbase);
        course.setId(id);
        return this.handling.path(course, this.user, request);
    }
}
