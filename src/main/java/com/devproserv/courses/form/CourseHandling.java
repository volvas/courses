package com.devproserv.courses.form;

import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;

import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

abstract class CourseHandling {

    abstract String courseIdParameter();

    abstract String errorMessageParameter();

    abstract void changeEntry(Course course, Student user);

    String path(Course course, Student user, HttpServletRequest request) {
        // TODO check the number of the course is not already subscribed
        changeEntry(course, user);
        user.prepareJspData(request);
        return STUDENT_PAGE;
    }
}
