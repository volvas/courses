package com.github.vovas11.courses.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code CommandFactory} is a factory (design pattern) that stores 
 * all commands and creates one when it needed
 * 
 * @author vovas11
 * @see Command
 */
public class CommandFactory {
    /**
     * {@code commandFactory} represents one instance of the CommandFactory (Singleton)
     */
    
    static CommandFactory commandFactory = new CommandFactory();
    /**
     * {@code commandMap} stores all commands
     */
    
    Map<String, Command> commandMap = new HashMap<>();
    /**
     * Private Constructor does not allow creating instances except by this class
     */
    
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
