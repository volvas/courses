package com.github.vovas11.courses.command;

import javax.servlet.http.HttpServletRequest;

import com.github.vovas11.courses.dao.*;

public class CourseSelectCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

	String courseIdStr = request.getParameter("coursenumber");
	int courseId = Integer.parseInt(courseIdStr);
	// TODO checking the valid input
	
	
	//DaoFactory daoFactory = DaoFactory.getInstance();
	// TODO
	
	return "/index.html";
    }
}
