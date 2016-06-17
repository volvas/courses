package com.github.vovas11.courses.command;

import javax.servlet.http.HttpServletRequest;

import com.github.vovas11.courses.dao.*;

public class RegistrationCommand implements Command {
    
    @Override
    public String execute(HttpServletRequest request) {
	String login = request.getParameter("login");
	String password = request.getParameter("password");
	String firstName = request.getParameter("firstname");
	String lastName = request.getParameter("lastname");
	String department = request.getParameter("department");
	DaoFactory daoFactory = DaoFactory.getInstance();
	UserDao users = daoFactory.getUserDao();
	User user = new User();
	user.setLogin(login);
	user.setPassword(password);
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setDepartment(department);
	if (users.isLoginExist(user)) {
	    return "/registration.html";
	} else {
	    users.insert(user);
	    return "/login.html";
	}
    }
}