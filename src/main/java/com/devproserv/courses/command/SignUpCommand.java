package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

import com.devproserv.courses.dao.DaoFactory;
import com.devproserv.courses.dao.User;
import com.devproserv.courses.dao.UserDao;

/**
 * {@code SignUpCommand} gets data about the new user from the HTTP request,
 * creates an instance of the {@code User}, writes the fields of the object and
 * sends it to the database via {@code UserDao}
 * 
 * @author vovas11
 * @see DaoFactory
 * @see UserDao
 */
public class SignUpCommand implements Command {
    /**
     * Gets data about the new user from the HTTP request,
     * creates an instance of the {@code User}, writes the fields of the object and
     * sends it to the DB via {@code UserDao}. Returns the name of the registration page
     * if the user exists or the name of the login page if the user have been registered.
     * 
     * @param   request   HTTP request
     * @return the the name of the page the server returns to the client
     */
    @Override
    public String execute(HttpServletRequest request) {
	
	/* gets parameters from the HTTP request */
	String login = request.getParameter("login");
	String password = request.getParameter("password");
	String firstName = request.getParameter("firstname");
	String lastName = request.getParameter("lastname");
	String department = request.getParameter("department");
	
	/* gets the link to the DaoFactory and UserDao */
	DaoFactory daoFactory = DaoFactory.getInstance();
	UserDao users = daoFactory.getUserDao();
	
	/* creates the new instance of the User and fills in fields */
	User user = new User();
	user.setLogin(login);
	user.setPassword(password);
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setDepartment(department);
	
	/* checks if the user (field 'login') exists and if yes returns back to the registration
	 * page, if no inserts new user into database and proceeds to the login page*/
	if (users.isLoginExist(user)) {
	    return "/signup.html";
	    
	} else {
	    users.insert(user);
	    return "/login.html";
	}
    }
}