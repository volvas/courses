package com.devproserv.courses.command;

import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.form.UnrollCourseHandling;
import com.devproserv.courses.servlet.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles course number user inputs to unsubscribe from desired courses
 * 
 */
public class Unroll implements Command {

    private AppContext appContext;


    public Unroll(AppContext appContext) {
        this.appContext = appContext;
    }


    @Override
    public String path(HttpServletRequest request) {
        return new EnrollForm(appContext, request, new UnrollCourseHandling()).validate();
    }
}
