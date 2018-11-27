package com.devproserv.courses.form;

import com.devproserv.courses.model.Student;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;

public class SignUpForm implements Form {

    private static final Logger logger = LogManager.getLogger(SignUpForm.class.getName());

    private final AppContext appContext;

    public SignUpForm(final AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        final String login      = request.getParameter("login");
        final String password   = request.getParameter("password");
        final String firstName  = request.getParameter("firstname");
        final String lastName   = request.getParameter("lastname");
        final String faculty    = request.getParameter("faculty");
        Validation validation = new SignUpValidation(login, password, firstName, lastName, faculty);
        return validation.validated() ? validPath(request) : invalidPath(validation, request);
    }

    private String invalidPath(Validation validation,
                               final HttpServletRequest request) {
        final String login      = request.getParameter("login");
        logger.info("Invalid credentials for potential login " + login);
        request.setAttribute("message", validation.errorMessage());
        return SIGNUP_PAGE;
    }

    private String validPath(final HttpServletRequest request) {
        final String login      = request.getParameter("login");
        final String password   = request.getParameter("password");
        final String firstName  = request.getParameter("firstname");
        final String lastName   = request.getParameter("lastname");
        final String faculty    = request.getParameter("faculty");
        return new Student(appContext, login, password, firstName, lastName, faculty).path(request);
    }
}
