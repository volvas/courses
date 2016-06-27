package com.github.vovas11.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.vovas11.courses.dao.*;

public class CourseSelectCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

	String courseIdStr = request.getParameter("coursenumber");
	int courseId = Integer.parseInt(courseIdStr);
	// TODO checking the valid input
	// TODO check the number of the course is not already subscribed
	
	HttpSession session = request.getSession(); // TODO
	
	
	
	DaoFactory daoFactory = DaoFactory.getInstance();
	CourseDao courses = daoFactory.getCourseDao();
	UserDao users = daoFactory.getUserDao();
	
	User user = new User(); // FIXME
	Course course = new Course(); // FIXME
	course.setId(courseId);
	
	courses.insertUserCourse(user, course);
	
	// Getting subscribed courses
	List<Course> subscribedCourses = courses.getSubscribedCourses(user);
	request.setAttribute("subscrcourses", subscribedCourses);

	// Getting all courses from the DB
	List<Course> courseList = courses.getAvailableCourses(user);
	request.setAttribute("courses", courseList);
	return "/courses.jsp";
    }
}
