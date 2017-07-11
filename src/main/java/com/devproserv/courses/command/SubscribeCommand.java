package com.devproserv.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.*;

/**
 * {@code CourseSelectCommand} handles the choice of the user that is subscribed to 
 * desired courses.
 * 
 * @author vovas11
 * @see DaoFactory
 * @see UserDao
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
	
	/* gets parameters from the request */
	String courseIdStr = request.getParameter("coursesubscrid");
	int courseId = Integer.parseInt(courseIdStr);
	
	/* checks the selection for the valid input */
	// TODO checking the valid input
	// TODO check the number of the course is not already subscribed
	
	/* gets the link to the current session or returns the login page
	 * if the session does not exist (e.g. timeout) */
	HttpSession session = request.getSession(false);
	if (session == null)
	    return "/login.html";
	
	/* gets the link to the current user and returns the login page
	 * if the user does not exist (e.g. timeout) */
	User user = (User) session.getAttribute(session.getId());
	if (user == null) 
	    return "/login.html";
	
	/* get links to CourseDao to handle request in the database*/
	CourseDao courses = appContext.getCourseDao();
	
	/* creates new instance of the course (the field 'id' is enough) */
	Course course = new Course();
	course.setId(courseId);
	
	/* performs the request in the database with current user and course */
	courses.insertUserCourse(user, course);
	
	/* Prepares the data for the JSP page:
	 * current user, subscribed courses and available courses */
	request.setAttribute("user", user);

	List<Course> subscribedCourses = courses.getSubscribedCourses(user);
	request.setAttribute("subscrcourses", subscribedCourses);

	List<Course> courseList = courses.getAvailableCourses(user);
	request.setAttribute("courses", courseList);
	
	return "/courses.jsp";
    }
}
