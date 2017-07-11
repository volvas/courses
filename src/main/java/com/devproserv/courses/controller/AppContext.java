package com.devproserv.courses.controller;

import static com.devproserv.courses.config.MainConfig.COMMAND_SIGNUP;
import static com.devproserv.courses.config.MainConfig.COMMAND_LOGIN;
import static com.devproserv.courses.config.MainConfig.COMMAND_LOGOUT;
import static com.devproserv.courses.config.MainConfig.COMMAND_SUBSCRIBE;
import static com.devproserv.courses.config.MainConfig.COMMAND_UNSUBSCRIBE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.LoginCommand;
import com.devproserv.courses.command.LogoutCommand;
import com.devproserv.courses.command.SignUpCommand;
import com.devproserv.courses.command.SubscribeCommand;
import com.devproserv.courses.command.UnsubscribeCommand;
import com.devproserv.courses.dao.CourseDao;
import com.devproserv.courses.dao.UserDao;

/**
 * {@code AppContext} is a main container controls application lifecycle
 *
 * @author vovas11
 */
public class AppContext {

    /** represents one instance of the Application Context (Singleton) */
    private static AppContext appContext = new AppContext();

    /** stores the link to the connection pool */
    Connection connection;
    /** stores all commands */
    private Map<String, Command> commandMap = new HashMap<>();

    /** constructor forbids the class instantiation from outside */
    private AppContext() {}

    /**
     * Provides the access to the single instance of the class
     * 
     * @return the link to the {@code AppContext} instance
     */
    public static AppContext getAppContext() {
        return appContext;
    }

    public void initBeans() {
        // fills the map with command instances
        commandMap.put(COMMAND_SIGNUP, new SignUpCommand(appContext));
        commandMap.put(COMMAND_LOGIN, new LoginCommand(appContext));
        commandMap.put(COMMAND_LOGOUT, new LogoutCommand());
        commandMap.put(COMMAND_SUBSCRIBE, new SubscribeCommand(appContext));
        commandMap.put(COMMAND_UNSUBSCRIBE, new UnsubscribeCommand(appContext));
        // gets link to database from servlet context
        try {
            InitialContext initContext = new InitialContext();
            DataSource datasrc = (DataSource) initContext.lookup("java:comp/env/jdbc/coursedb");
            connection = datasrc.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Delivers an instance of the command by its name
     *
     * @param key name of the command
     * @return the instance of the {@code Command} corresponding to name
     */
    public Command getCommand(String key) {
        return commandMap.get(key);
    }

    /**
     * Delivers an instance of the {@code UserDao} class
     * 
     * @return link to the instance of UserDao
     */
    public UserDao getUserDao() {
        return new UserDao(connection);
    }

    /**
     * Delivers an instance of the {@code CourseDao} class
     * 
     * @return link to the instance of CourseDao
     */
    public CourseDao getCourseDao() {
        return new CourseDao(connection);
    }
}
