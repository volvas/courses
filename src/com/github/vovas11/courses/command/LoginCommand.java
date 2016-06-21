package com.github.vovas11.courses.command;

/**
 * Handles access for the existing user
 * 
 * @author vovas11
 * 
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.vovas11.courses.dao.*;

public class LoginCommand implements Command {
    
    /**
     * Defines if the name and password of the user exists and if yes,
     * returns the page for the user. If not, returns the home page
     *
     * @param   request   HTTP request
     * @return the the name of the page the server returns to the client
     */
    @Override
    public String execute(HttpServletRequest request) {
	String login = request.getParameter("login");
	String password = request.getParameter("password");
	
	DaoFactory daoFactory = DaoFactory.getInstance();
	
	UserDao users = daoFactory.getUserDao();
	CourseDao courses = daoFactory.getCourseDao();

	User user = new User();
	user.setLogin(login);
	user.setPassword(password);

	if (users.isExist(user)) {
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
