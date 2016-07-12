package com.github.vovas11.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.vovas11.courses.dao.*;

/**
 * {@code LoginCommand} handles access for the existing user
 * 
 * @author vovas11
 * @see DaoFactory
 * @see UserDao
 * @see CourseDao
 * 
 */
public class LoginCommand implements Command {
    /**
     * Defines if the name and password of the user exists in the database and if yes,
     * returns the page for the user. If not, returns the starting page
     *
     * @param   request   HTTP request
     * @return the the name of the page the server returns to the client
     */
    @Override
    public String execute(HttpServletRequest request) {
	
	/* gets parameters from the HTTP request */
	String login = request.getParameter("login");
	String password = request.getParameter("password");
	
	/* gets the link to the DaoFactory and UserDao and CourseDao */
	DaoFactory daoFactory = DaoFactory.getInstance();
	UserDao users = daoFactory.getUserDao();
	CourseDao courses = daoFactory.getCourseDao();
	
	/* creates new user and sets the fields received from the user form via HTTP request */
	User user = new User();
	user.setLogin(login);
	user.setPassword(password);
	
	/* checks if the user with entered credentials exists in the database:
	 * yes - proceed, no - forwards to the starting page */
	if (users.isExist(user)) {
	    /* gets the link to the current session or creates new one */
	    HttpSession session = request.getSession();
	    
	    /* gets all fields for the user from the database and set the link to the session */
	    users.getFieldsForUser(user);
	    session.setAttribute(session.getId(), user);
	    
	    /* prepares the data for the JSP page:
	     * current user, subscribed courses and available courses */
	    request.setAttribute("user", user);
	    
	    /* gets subscribed courses from the database and set the link to the HTTP request */
	    List<Course> subscribedCourses = courses.getSubscribedCourses(user);
	    request.setAttribute("subscrcourses", subscribedCourses);
	    
	    /* gets available courses from the database and set the link to the HTTP request */
	    List<Course> courseList = courses.getAvailableCourses(user);
	    request.setAttribute("courses", courseList);
	    return "/courses.jsp";
	    
	} else {
	    return "/index.html";
	}
    }
}
