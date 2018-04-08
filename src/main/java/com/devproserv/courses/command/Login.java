package com.devproserv.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devproserv.courses.util.Validation;
import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.User;
import com.devproserv.courses.dao.UserDao;
import com.devproserv.courses.dao.CourseDao;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;
import static com.devproserv.courses.config.MainConfig.LECTURER_PAGE;
/**
 * {@code Login} handles access for the existing user. After user input valid
 * credentials he/she gets the own page
 * 
 * @author vovas11
 * @see UserDao
 * @see CourseDao
 * 
 */
public class Login implements Command {
    
    /** Injection of the main app manager */
    private AppContext appContext;


    public Login(AppContext appContext) {
        this.appContext = appContext;
    }
    
    /**
     * Validates user name and password, checks if credentials exist in database
     *
     * @param request HTTP request
     * @return own page to client if credentials are valid or the same page
     */
    @Override
    public String path(HttpServletRequest request) {

        /* gets parameters from the HTTP request */
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        /* checks if login and password are valid */
        String validResponse = Validation.checkCredentials(login, password);

        if (!validResponse.equals("ok")) {
            request.setAttribute("message", validResponse);
            return LOGIN_PAGE;
        }

        UserDao userDao = appContext.getUserDao();
        /* checks if the user with entered credentials exists in the database */
        if (!userDao.userExists(login, password)) {
            validResponse = "Wrong username or password! Try again!";
            request.setAttribute("message", validResponse);
            return LOGIN_PAGE;
        }
        
        /* gets a link to instance of User class and gets all data for the user */
        User user = userDao.getUser(login, password);
        /* gets the link to the current session or creates new one and attaches the user to the session */
        HttpSession session = request.getSession(); // TODO add login.jsp filter to check validated session
        session.setAttribute(session.getId(), user);
        
        /* Define a page where the user will be redirected according to user role */
        String path = LOGIN_PAGE;
        switch (user.getRole()) {
        case STUD:
            prepareStudentPage(request, user);
            path = STUDENT_PAGE;
            break;
        case LECT:
            prepareLecturerPage(request, user);
            path = LECTURER_PAGE;
            break;
        case ADMIN:
            request.setAttribute("message", "This account is not accessible!");
            path = LOGIN_PAGE;
            break;
        }
        return path;
    }

    /**
     * Prepares data to be displayed on the own student's page and attaches the data
     * to HTTP request. Then JSP servlet will handle this request.
     * 
     * @param request HTTP request
     * @param user user
     */
    private void prepareStudentPage(HttpServletRequest request, User user) {
        CourseDao courses = appContext.getCourseDao();
        /* prepares the data for the JSP page */
        request.setAttribute("student", user);
        /* gets subscribed courses and available courses */
        List<Course> subscribedCourses = courses.getSubscribedCourses(user);
        request.setAttribute("subscrcourses", subscribedCourses);
        List<Course> availableCourses = courses.getAvailableCourses(user);
        request.setAttribute("courses", availableCourses);
    }
    
    /**
     * Prepares data to be displayed on the own lecturer's page and attaches the data
     * to HTTP request. Then JSP servlet will handle this request.
     * 
     * @param request HTTP request
     * @param user user
     */
    private void prepareLecturerPage(HttpServletRequest request, User user) {
        // TODO
    }
}