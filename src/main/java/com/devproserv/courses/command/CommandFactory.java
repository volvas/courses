package com.devproserv.courses.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code CommandFactory} is a factory (design pattern) that stores all commands
 * needed for handling user forms and returns one of commands when it needed
 *
 * @author vovas11
 * @see Command
 */
public class CommandFactory {

    /* represents one instance of the CommandFactory (Singleton) */
    private static CommandFactory commandFactory = new CommandFactory();

    /* stores all commands */
    private Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
        commandMap.put("registration", new RegistrationCommand());
        commandMap.put("login", new LoginCommand());
        commandMap.put("courseselect", new CourseSelectCommand());
        commandMap.put("logout", new LogoutCommand());
    }

    /**
     * Provides the access to the single instance of the class
     * @return the link to the {@code CommandFactory} instance
     */
    public static CommandFactory getInstance() {
        return commandFactory;
    }

    /**
     * Delivers an object of the command the name of which is sent inside the HTTP request
     *
     * @param   request   HTTP request from the servlet
     * @return the single instance of the {@code Command}
     */
    public Command getCommand(HttpServletRequest request) {
        String value = request.getParameter("ok");
        return commandMap.get(value);
    }
}