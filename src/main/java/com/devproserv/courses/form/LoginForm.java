package com.devproserv.courses.form;

import com.devproserv.courses.model.PrelUser;
import com.devproserv.courses.servlet.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

/**
 * Represents a login form on web page
 */
public class LoginForm implements Form {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginForm.class);

    private final AppContext appContext;


    public LoginForm(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        final String login          = request.getParameter("login");
        final String password       = request.getParameter("password");
        final Validation validation = new LoginValidation(login, password);
        if (validation.validated()) {
            LOGGER.debug("Login '{}' and password are valid.", login);
            return validPath(request, login, password);
        } else {
            return invalidPath(validation, request, login);
        }
    }

    private String invalidPath(final Validation validation, final HttpServletRequest request, final String login) {
        LOGGER.info("Invalid credentials for login {}", login);
        request.setAttribute("message", validation.errorMessage());
        return LOGIN_PAGE;
    }

    private String validPath(final HttpServletRequest request, final String login, final String password) {
        return new PrelUser(appContext, login, password).path(request);
    }
}
