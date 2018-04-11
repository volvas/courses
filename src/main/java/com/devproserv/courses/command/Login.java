package com.devproserv.courses.command;

import com.devproserv.courses.servlet.AppContext;
import com.devproserv.courses.temp.LoginForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Treats data from login web form
 *
 */
public class Login implements Command {
    
    private AppContext appContext;


    public Login(AppContext appContext) {
        this.appContext = appContext;
    }


    @Override
    public String path(HttpServletRequest request) {
        return new LoginForm(appContext, request).validate();
    }
}