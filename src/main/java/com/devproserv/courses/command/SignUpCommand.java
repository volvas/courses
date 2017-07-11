package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.Student;
import com.devproserv.courses.dao.UserDao;
import com.devproserv.courses.util.Validation;

import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;
import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

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

    /** Injection of the main app manager */
    private AppContext appContext;


    public SignUpCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Gets data about the new user (student) from the HTTP request,
     * creates an instance of the {@code Student}, writes the fields of the object and
     * sends it to the DB via {@code UserDao}. Returns the name of the registration page
     * if the user exists or the name of the login page if the user have been successfully registered.
     * 
     * @param request HTTP request
     * @return the the name of the page the server returns to the client
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
        
        /* gets the link to UserDao */
        UserDao users = appContext.getUserDao();

        /* creates the new instance of the User and fills in fields */
        Student user = new Student();
        user.setLogin(login);
        
        /* checks if the user (field 'login') exists and if yes returns back to the registration
         * page, if no inserts new user into database and proceeds to the login page*/
        if (users.loginExists(user)) {
            request.setAttribute("message", "User allready exists!");
            return SIGNUP_PAGE;
        } else {
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setStudentRole();
            user.setFaculty(faculty);
            
            if (users.insertStudent(user)) {
                return LOGIN_PAGE;
            } else {
                return SIGNUP_PAGE;
            }
        }
    }
}