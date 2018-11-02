package com.devproserv.courses.form;

import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

public class EnrollForm implements Form {

    private static final Logger logger = LogManager.getLogger(EnrollForm.class.getName());

    private final AppContext appContext;
    private final HttpServletRequest request;
    private final CourseHandling courseHandling;
    private final String courseIdStr;

    private Student user;


    public EnrollForm(AppContext appContext, HttpServletRequest request, CourseHandling courseHandling) {
        this.appContext = appContext;
        this.request = request;
        this.courseHandling = courseHandling;
        this.courseIdStr = request.getParameter(courseHandling.courseIdParameter());
    }

    @Override
    public String validate(final HttpServletRequest request) {
        // check if session still exists
        HttpSession session = request.getSession(false);
        if (session == null) {
            return LOGIN_PAGE;
        }

        user = (Student) session.getAttribute(session.getId());
        if (user == null) {
            return LOGIN_PAGE;
        }

        /* checks the selection for the valid input */
        Validation validation = new NumberValidation(courseIdStr);
        return validation.validated() ? validPath() : invalidPath(validation);
    }

    private String invalidPath(Validation validation) {
        logger.info("Invalid credentials for login " + user.getLogin());
        request.setAttribute(courseHandling.errorMessageParameter(), validation.errorMessage());
        user.prepareJspData(request);
        return STUDENT_PAGE;
    }

    private String validPath() {
        int courseId = Integer.parseInt(courseIdStr);

        Course course = new Course(appContext);
        course.setId(courseId);
        return courseHandling.path(course, user, request);
    }
}
