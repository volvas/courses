package com.devproserv.courses.controller;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.LoginCommand;
import com.devproserv.courses.command.LogoutCommand;
import com.devproserv.courses.command.SignUpCommand;
import com.devproserv.courses.command.SubscribeCommand;
import com.devproserv.courses.command.UnsubscribeCommand;
import com.devproserv.courses.dao.CourseDao;
import com.devproserv.courses.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.devproserv.courses.config.MainConfig.COMMAND_LOGIN;
import static com.devproserv.courses.config.MainConfig.COMMAND_LOGOUT;
import static com.devproserv.courses.config.MainConfig.COMMAND_SIGNUP;
import static com.devproserv.courses.config.MainConfig.COMMAND_SUBSCRIBE;
import static com.devproserv.courses.config.MainConfig.COMMAND_UNSUBSCRIBE;
import static com.devproserv.courses.config.MainConfig.NOT_FOUND_PAGE;

/**
 * {@code AppContext} is a main container controls application lifecycle
 *
 * @author vovas11
 */
public class AppContext {

    private static final Logger logger = LogManager.getLogger(AppContext.class.getName());

    private DataSource dataSource;
    private final Map<String, Command> commandMap = new HashMap<>();


    AppContext() {
        initBeans();
        logger.info("Beans initialized.");
    }


    private void initBeans() {
        // fill the map with command instances
        commandMap.put(COMMAND_SIGNUP, new SignUpCommand(this));
        commandMap.put(COMMAND_LOGIN, new LoginCommand(this));
        commandMap.put(COMMAND_LOGOUT, new LogoutCommand());
        commandMap.put(COMMAND_SUBSCRIBE, new SubscribeCommand(this));
        commandMap.put(COMMAND_UNSUBSCRIBE, new UnsubscribeCommand(this));
        // get link to database from servlet context
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/coursedb");
        } catch (NamingException e) {
            logger.error("Database not found!", e);
        }
    }

    /**
     * Delivers a path of a page defined by given request
     *
     * @param request HTTP request
     * @return a string with a path defined by parameter "command" in request
     */
    String getPath(HttpServletRequest request) {
        final String commandRequest = request.getParameter("command");
        final Command command = commandMap.get(commandRequest);

        return (command == null) ? NOT_FOUND_PAGE
                                 : command.executeCommand(request);
    }

    /**
     * Delivers an instance of the {@code UserDao} class
     * 
     * @return link to the instance of UserDao
     */
    public UserDao getUserDao() {
        return new UserDao(dataSource);
    }

    /**
     * Delivers an instance of the {@code CourseDao} class
     * 
     * @return link to the instance of CourseDao
     */
    public CourseDao getCourseDao() {
        return new CourseDao(dataSource);
    }
}
