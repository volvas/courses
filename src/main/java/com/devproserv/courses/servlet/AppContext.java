package com.devproserv.courses.servlet;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.Login;
import com.devproserv.courses.command.Logout;
import com.devproserv.courses.command.NotFound;
import com.devproserv.courses.command.SignUp;
import com.devproserv.courses.command.Subscribe;
import com.devproserv.courses.command.Unsubscribe;
import com.devproserv.courses.dao.CourseDao;
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

/**
 * {@code AppContext} is a main container controls application lifecycle
 *
 * @author vovas11
 */
public class AppContext {

    private static final Logger logger = LogManager.getLogger(AppContext.class.getName());
    private static final String DB_URL = "java:comp/env/jdbc/coursedb";

    private DataSource dataSource;
    private final Map<String, Command> commandMap = new HashMap<>();


    AppContext() {
        initBeans();
        logger.info("Beans initialized.");
    }


    private void initBeans() {
        // define command list handling web forms
        commandMap.put(COMMAND_SIGNUP, new SignUp(this));
        commandMap.put(COMMAND_LOGIN, new Login(this));
        commandMap.put(COMMAND_LOGOUT, new Logout());
        commandMap.put(COMMAND_SUBSCRIBE, new Subscribe(this));
        commandMap.put(COMMAND_UNSUBSCRIBE, new Unsubscribe(this));
        // get link to database from servlet context
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup(DB_URL);
        } catch (NamingException e) {
            logger.fatal("Database with path \"" + DB_URL + "\" not found!", e);
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
        Command command = commandMap.get(commandRequest);
        if (command == null) {
            logger.warn("Invalid command given!");
            command = new NotFound();
        }
        return command.path(request);
    }

    public DataSource getDataSource() {
        return dataSource;
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
