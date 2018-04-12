package com.devproserv.courses.form;

import com.devproserv.courses.model.Student;
import com.devproserv.courses.model.User;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;

public class SignUpForm implements Form {

    private static final Logger logger = LogManager.getLogger(SignUpForm.class.getName());

    private final AppContext appContext;
    private final HttpServletRequest request;
    private final String login;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String faculty;


    public SignUpForm(AppContext appContext, HttpServletRequest request) {
        this.appContext = appContext;
        this.request    = request;
        this.login      = request.getParameter("login");
        this.password   = request.getParameter("password");
        this.firstName  = request.getParameter("firstname");
        this.lastName   = request.getParameter("lastname");
        this.faculty    = request.getParameter("faculty");
    }

    @Override
    public String validate() {
        Validation validation = new SignUpValidation(login, password, firstName, lastName, faculty);
        return validation.validated() ? validPath() : invalidPath(validation);
    }

    private String invalidPath(Validation validation) {
        logger.info("Invalid credentials for potential login " + login);
        request.setAttribute("message", validation.errorMessage());
        return SIGNUP_PAGE;
    }

    private String validPath() {
        return new Student(appContext).path(request);
    }
}
