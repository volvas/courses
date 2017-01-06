package com.devproserv.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devproserv.courses.controller.Validation;
import com.devproserv.courses.dao.*;

/**
 * {@code LoginCommand} handles access for the existing user. After user input valid
 * credentials he/she gets a page with his/her courses
 * 
 * @author vovas11
 * @see DaoFactory
 * @see UserDao
 * @see CourseDao
 * 
 */
public class LoginCommand implements Command {
    /**
     * Validates user name and password, checks if credentials exist in database
     *
     * @param request HTTP request
     * @return the the name of the page the server returns to the client
     */
    @Override
    public String executeCommand(HttpServletRequest request) {

        /* gets parameters from the HTTP request */
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        /* checks if login and password are valid */
        String validResponse = Validation.checkCredentials(login, password);

        if (!validResponse.equals("ok")) {
            request.setAttribute("message", validResponse);
            return "/login.jsp";
        }

        /* gets the link to the DaoFactory and UserDao and CourseDao */
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao users = daoFactory.getUserDao();
        CourseDao courses = daoFactory.getCourseDao();

        /* creates new user and sets the fields received from the user form via HTTP request */
        Student user = new Student(); // TODO User replaced with Student
        user.setLogin(login);
        user.setPassword(password);

        /* checks if the user with entered credentials exists in the database */
        if (!users.isExist(user)) {
            validResponse = "Wrong username or password! Try again!";
            request.setAttribute("message", validResponse);
            return "/login.jsp";
        }
        
        // TODO to check field 'role' and define how to proceed: Student, Lecturer, Admin
        /* gets the link to the current session or creates new one */
        HttpSession session = request.getSession();

        /* gets all data for the user and assigns to the session */
        users.getFieldsForUser(user);
        session.setAttribute(session.getId(), user);

        /* prepares the data for the JSP page */
        request.setAttribute("user", user);

        /* gets subscribed courses and available courses */
        List<Course> subscribedCourses = courses.getSubscribedCourses(user);
        request.setAttribute("subscrcourses", subscribedCourses);

        List<Course> courseList = courses.getAvailableCourses(user);
        request.setAttribute("courses", courseList);
        
        return "/courses.jsp";
    }
}
