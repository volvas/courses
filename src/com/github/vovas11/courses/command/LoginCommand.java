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
     * returns the page for the user. If not, returns the home page
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
	
	/* gets the link to the current session or creates new one */
	HttpSession session = request.getSession();
	
	/* gets the link to the current user and if one does not exists yet
	 * creates new user instance and updates all fields */
	User user = (User) session.getAttribute(session.getId());
	
	// FIXME check the user credential after checking existence of the login
	
	if (user == null) {
	    user = new User();
	    user.setLogin(login);
	    user.setPassword(password);
	    
	    // gets all fields for the object from the database
	    users.getFieldsForUser(user);
	    session.setAttribute(session.getId(), user);
	}
	
	/* Checks if the user already Prepares the data for the JSP page:
	 * current user, subscribed courses and available courses */
	if (users.isExist(user)) {
	    // Information about the logged user
	    request.setAttribute("user", user);
	    // Getting subscribed courses
	    List<Course> subscribedCourses = courses.getSubscribedCourses(user);
	    request.setAttribute("subscrcourses", subscribedCourses);
	    
	    // Getting all courses from the DB
	    List<Course> courseList = courses.getAvailableCourses(user);
	    request.setAttribute("courses", courseList);
	    return "/courses.jsp";
	} else {
	    return "/index.html";
	}
    }
}
