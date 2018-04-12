package com.devproserv.courses.model;

import com.devproserv.courses.servlet.AppContext;

import javax.servlet.http.HttpServletRequest;


/**
 * Represents the entity of the Administrator.
 *
 */
public class Admin extends User {

    private final AppContext appContext;


    public Admin(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public boolean exists() {
        return true; // TODO
    }

    @Override
    public void loadFields() {
        // TODO
    }

    @Override
    public void prepareJspData(HttpServletRequest request) {
        // TODO
        request.setAttribute("message", "This account is not accessible!");
    }
}
