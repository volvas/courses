package com.devproserv.courses.command;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.CourseDao;
import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.User;
import com.devproserv.courses.util.Validation;

/**
 * {@code CourseSelectCommand} handles the choice of the user that is subscribed to 
 * desired courses.
 * 
 * @author vovas11
 * @see CourseDao
 */
public class SubscribeCommand implements Command {

    /** Injection of the main app manager */
    private AppContext appContext;


    public SubscribeCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Retrieves the number of the selected course, checks if the choice is valid,
     * makes changes in the database and returns the same page but with changed tables
     * 
     * @param   request   HTTP request
     * @return the the name of the page the server returns to the client (in this
     * case the same page)
     */
    @Override
    public String executeCommand(HttpServletRequest request) {

        /* gets the link to the current session or returns the login page
         * if the session does not exist (e.g. timeout) */
        HttpSession session = request.getSession(false);
        if (session == null) {
            return LOGIN_PAGE;
        }
        /* gets the link to the current user and returns the login page
         * if the user does not exist (e.g. timeout) */
        User user = (User) session.getAttribute(session.getId());
        if (user == null) {
            return LOGIN_PAGE;
        }
        /* get links to CourseDao to handle request in the database*/
        CourseDao courses = appContext.getCourseDao();
        
        /* gets parameters from the request */
        String courseIdStr = request.getParameter("coursesubscrid");
        /* checks the selection for the valid input */
        String validResponse = Validation.checkInteger(courseIdStr);
        if (!validResponse.equals("ok")) {
            request.setAttribute("messagesub", validResponse);
            prepareJsp(request, user, courses);
            return STUDENT_PAGE;
        }
        int courseId = Integer.parseInt(courseIdStr);

        // TODO check the number of the course is not already subscribed

        /* creates new instance of the course (the field 'id' is enough) */
        Course course = new Course();
        course.setId(courseId);

        /* performs the request in the database with current user and course */
        courses.insertUserCourse(user, course);

        prepareJsp(request, user, courses);

        return STUDENT_PAGE;
    }

    /**
     * Prepares the data for the JSP page: current user, subscribed courses and available courses 
     * 
     * @param request request
     * @param user user
     * @param courses courses
     */
    private void prepareJsp(HttpServletRequest request, User user, CourseDao courses) {
        request.setAttribute("student", user);

        List<Course> subscribedCourses = courses.getSubscribedCourses(user);
        request.setAttribute("subscrcourses", subscribedCourses);

        List<Course> courseList = courses.getAvailableCourses(user);
        request.setAttribute("courses", courseList);
    }
}
