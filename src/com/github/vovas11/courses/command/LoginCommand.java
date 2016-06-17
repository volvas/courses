package com.github.vovas11.courses.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.vovas11.courses.dao.*;

public class LoginCommand implements Command {
    
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
	    List<Course> courseList = courses.getAllCourses();
	    request.setAttribute("courses", courseList);
	    return "/courses.jsp";
	} else {
	    return "/index.html";
	}
    }
}
