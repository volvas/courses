package com.devproserv.courses.command;

import com.devproserv.courses.servlet.AppContext;
import com.devproserv.courses.form.LoginForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Treats data from login web form
 */
public class Login implements Command {
    private LoginForm loginForm;


    public Login(AppContext appContext) {
        this(new LoginForm(appContext));
    }

    /**
     * Primary constructor
     *
     * @param loginForm login form instance
     */
    public Login(final LoginForm loginForm) {
        this.loginForm = loginForm;
    }


    @Override
    public String path(final HttpServletRequest request) {
        return loginForm.validate(request);
    }
}