package com.devproserv.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devproserv.courses.util.Validation;
import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.Course;
import com.devproserv.courses.dao.CourseDao;
import com.devproserv.courses.dao.Student;
import com.devproserv.courses.dao.User;
import com.devproserv.courses.dao.UserDao;

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
    
    /** Injection of the main app manager */
    private AppContext appContext;


    public LoginCommand(AppContext appContext) {
        this.appContext = appContext;
    }
    
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

        /* gets the link to the and UserDao */
        UserDao users = appContext.getUserDao();
        
        /* creates a new user instance and sets the fields received from the user form via HTTP request */
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        
        /* checks if the user with entered credentials exists in the database */
        if (!users.userExists(user)) {
            validResponse = "Wrong username or password! Try again!";
            request.setAttribute("message", validResponse);
            return "/login.jsp";
        }
        
        users.getUserRole(user);
        
        if (user.isRoleStudent()) {
            /* gets the link to the CourseDao */
            CourseDao courses = appContext.getCourseDao();
            
            /* gets the link to the current session or creates new one */
            HttpSession session = request.getSession();
            
            
            /* creates new student instance and copies fields from the user */
            Student student = new Student();
            student.setLogin(user.getLogin());
            student.setPassword(user.getPassword());
            student.setRole(user.getRole());
            
            
            /* gets all data for the user and assigns to the session */
            users.appendRestUserFields(student);
            session.setAttribute(session.getId(), user);
            
            /* prepares the data for the JSP page */
            request.setAttribute("student", student);
            
            /* gets subscribed courses and available courses */
            List<Course> subscribedCourses = courses.getSubscribedCourses(user);
            request.setAttribute("subscrcourses", subscribedCourses);

            List<Course> courseList = courses.getAvailableCourses(user);
            request.setAttribute("courses", courseList);
            
            return "/courses.jsp";
            
        } else if (user.isRoleLecturer()) {
            // TODO
        } else {
            
        }
        
        return "/login.jsp";
    }
}
