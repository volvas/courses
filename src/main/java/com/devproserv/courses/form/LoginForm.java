package com.devproserv.courses.form;

import com.devproserv.courses.model.PrelUser;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

public class LoginForm implements Form {

    private static final Logger logger = LogManager.getLogger(LoginForm.class.getName());

    private final AppContext appContext;
    private final HttpServletRequest request;
    private final String login;
    private final String password;



    public LoginForm(AppContext appContext, HttpServletRequest request) {
        this.appContext = appContext;
        this.request = request;
        this.login = request.getParameter("login");
        this.password = request.getParameter("password");
    }

    @Override
    public String validate() {
        Validation validation = new LoginValidation(login, password);
        return validation.validated() ? validPath() : invalidPath(validation);
    }

    private String invalidPath(Validation validation) {
        logger.info("Invalid credentials for login " + login);
        request.setAttribute("message", validation.errorMessage());
        return LOGIN_PAGE;
    }

    private String validPath() {
        return new PrelUser(appContext, login, password).path(request);
    }
}
