package com.devproserv.courses.command;

import com.devproserv.courses.form.SignUpForm;
import com.devproserv.courses.servlet.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code SignUp} creates a new user using data from the HTTP request,
 * and stores the data in the database
 * 
 */
public class SignUp implements Command {

    private AppContext appContext;


    public SignUp(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Gets data about the new user (student) from the HTTP request,
     * checks if data are valid, and send the instance to store in the database.
     * 
     * @param request HTTP request
     * @return login page name if the user has been created successfully
     * or the same sign up page name in case of wrong data
     */
    @Override
    public String path(HttpServletRequest request) {
        return new SignUpForm(appContext, request).validate();
    }
}
