package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.UserDao;
import com.devproserv.courses.util.Validation;

import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;
import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

/**
 * {@code SignUpCommand} creates a new user using data from the HTTP request,
 * and stores the data in the database
 * 
 * @author vovas11
 * @see UserDao
 */
public class SignUpCommand implements Command {

    /** Injection of the main app manager */
    private AppContext appContext;


    public SignUpCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Gets data about the new user (student) from the HTTP request,
     * checks if data are valid, and send the instance to store in the database.
     * 
     * @param request HTTP request
     * @return login page name if the user has been created successfully
     * or the same sign up page name in case of wrong data
     */
    @Override
    public String executeCommand(HttpServletRequest request) {

        /* gets parameters from the HTTP request */
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String faculty = request.getParameter("faculty");
        
        /* checks if input fields are valid */
        String validResponse = Validation.checkCredentials(login, password, firstName, lastName, faculty);

        if (!validResponse.equals("ok")) {
            request.setAttribute("message", validResponse);
            return SIGNUP_PAGE;
        }
        
        UserDao userDao = appContext.getUserDao();
        /* checks if login exists and if yes returns back to the sign up
         * page, if no inserts new user into database and proceeds to the login page*/
        if (userDao.loginExists(login)) {
            request.setAttribute("message", "User allready exists!");
            return SIGNUP_PAGE;
        } else if (userDao.createUser(login, password, firstName, lastName, faculty)) {
            return LOGIN_PAGE;
        } else {
            request.setAttribute("message", "User has not been created. Try again.");
            return SIGNUP_PAGE;
        }
    }
}