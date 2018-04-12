package com.devproserv.courses.model;

import com.devproserv.courses.servlet.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents the entity of the Lecturer. Maps the table 'lecturers' in the database.
 *
 */
public class Lecturer extends User {

    private final AppContext appContext;

    // additional fields representing columns in the table 'lecturers'
    private String degree;

    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }


    public Lecturer(AppContext appContext) {
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
