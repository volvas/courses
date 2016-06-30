package com.github.vovas11.courses.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code CommandFactory} is a factory (design pattern) that stores 
 * all commands and creates when it needed
 * 
 * 
 * @author vovas11
 *
 */

public class CommandFactory {
    static CommandFactory commandFactory = new CommandFactory();
    Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
	commandMap.put("registration", new RegistrationCommand());
	commandMap.put("login", new LoginCommand());
	commandMap.put("courseselect", new CourseSelectCommand());
    }
    
    public static CommandFactory getInstance() {
	return commandFactory;
    }
    
    /**
     * Allows to get the object of the command by name
     * 
     * @param request
     * @return
     */
    public Command getCommand(HttpServletRequest request) {
	String value = request.getParameter("ok");
	return commandMap.get(value);
    }
}
