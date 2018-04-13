package com.devproserv.courses.command;

import com.devproserv.courses.form.EnrollCourseHandling;
import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.servlet.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles course number user inputs to subscribe to desired courses
 * 
 */
public class Enroll implements Command {

    private AppContext appContext;


    public Enroll(AppContext appContext) {
        this.appContext = appContext;
    }


    @Override
    public String path(HttpServletRequest request) {
        return new EnrollForm(appContext, request, new EnrollCourseHandling()).validate();
    }
}
